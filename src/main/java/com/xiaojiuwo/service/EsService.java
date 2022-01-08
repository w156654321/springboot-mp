package com.xiaojiuwo.service;

import com.alibaba.fastjson.JSON;
import com.xiaojiuwo.model.ContentModel;
import com.xiaojiuwo.model.EsConstant;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.util.CollectionUtils;
import org.elasticsearch.core.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.ScoreSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.xcontent.XContentType;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.xiaojiuwo.config.EsConfig.COMMON_OPTIONS;

/**
 * es操作类
 * @author
 */
@Slf4j
@Service
public class EsService {


    @Resource
    private RestHighLevelClient restHighLevelClient;

    /**
     * @param index
     * @param from
     * @param size
     * @param queryBuilder
     * @param sortFieldsToAsc
     * @param includeFields
     * @param excludeFields
     * @param timeOut
     * @return
     */
    public List<Map<String, Object>> searchIndex(String index, int from, int size, QueryBuilder queryBuilder,
                                                 Map<String, Boolean> sortFieldsToAsc, String[] includeFields, String[] excludeFields,
                                                 int timeOut) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        try {
            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
            //条件
            if (queryBuilder != null) {
                BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
                boolQueryBuilder.must(queryBuilder);
                sourceBuilder.query(boolQueryBuilder);
                //where.forEach((k, v) -> {
                //    if (v instanceof Map) {
                //        //范围选择map  暂定时间
                //        Map<String, Date> mapV = (Map<String, Date>) v;
                //        if (mapV != null) {
                //            boolQueryBuilder.must(
                //                    QueryBuilders.rangeQuery(k).
                //                            gte(format.format(mapV.get("start"))).
                //                            lt(format.format(mapV.get("end"))));
                //        }
                //    } else {
                //        //普通模糊匹配
                //        //boolQueryBuilder.must(QueryBuilders.wildcardQuery(k, v.toString()));
                //        boolQueryBuilder.must(QueryBuilders.multiMatchQuery(k, v.toString()));
                //        //boolQueryBuilder.must(QueryBuilders.multiMatchQuery(k, v.toString()));
                //    }
                //});
                //sourceBuilder.query(boolQueryBuilder);
            }

            //分页
            from = from <= -1 ? 0 : from;
            size = size >= 1000 ? 1000 : size;
            size = size <= 0 ? 15 : size;
            sourceBuilder.from(from);
            sourceBuilder.size(size);

            //超时
            sourceBuilder.timeout(new TimeValue(timeOut, TimeUnit.SECONDS));

            //排序
            if (sortFieldsToAsc != null && !sortFieldsToAsc.isEmpty()) {
                sortFieldsToAsc.forEach((k, v) -> {
                    sourceBuilder.sort(new FieldSortBuilder(k).order(v ? SortOrder.ASC : SortOrder.DESC));
                });
            } else {
                sourceBuilder.sort(new ScoreSortBuilder().order(SortOrder.DESC));
            }

            //返回和排除列
            if (!CollectionUtils.isEmpty(includeFields) || !CollectionUtils.isEmpty(excludeFields)) {
                sourceBuilder.fetchSource(includeFields, excludeFields);
            }

            SearchRequest rq = new SearchRequest();
            //索引
            rq.indices(index);
            //各种组合条件
            rq.source(sourceBuilder);

            //请求
            System.out.println(rq.source().toString());
            SearchResponse rp = restHighLevelClient.search(rq,COMMON_OPTIONS);

            //解析返回
            //if (rp.status() != RestStatus.OK || rp.getHits().getTotalHits() <= 0) {
            if (rp.status() != RestStatus.OK) {
                return Collections.emptyList();
            }

            //获取source
            return Arrays.stream(rp.getHits().getHits()).map(b -> {
                return b.getSourceAsMap();
            }).collect(Collectors.toList());

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return Collections.emptyList();
    }


    /**
     * 批量插入文档数据
     * @return 返回true 表示有错误
     */
    public boolean batchEsContent(List<ContentModel> contentModelList) throws IOException {
        BulkRequest bulkRequest = new BulkRequest();
        List<Integer> delIds=new LinkedList<>();
        for (ContentModel model : contentModelList) {
            IndexRequest indexRequest = new IndexRequest(EsConstant.CONTENT_INDEX);
            indexRequest.id(model.getContentId().toString());
            String s = JSON.toJSONString(model);
            indexRequest.source(s, XContentType.JSON);
            bulkRequest.add(indexRequest);
        }
        BulkResponse bulk = restHighLevelClient.bulk(bulkRequest, COMMON_OPTIONS);
        List<String> collect = Arrays.stream(bulk.getItems()).map(item -> {
            return item.getId();
        }).collect(Collectors.toList());
        log.error("内容索引生成：{}", collect);
        return bulk.hasFailures();
    }

    /**
     * 插入文档数据
     * @return
     */
    public boolean insertEsContent(String indexName,String id,String json) throws IOException {
        IndexRequest indexRequest = new IndexRequest(indexName);
        indexRequest.id(id);
        indexRequest.source(json, XContentType.JSON);
        IndexResponse response = restHighLevelClient.index(indexRequest, COMMON_OPTIONS);
      ;  if(response.getResult().name().equalsIgnoreCase("created")){
            log.info("内容索引生成：{}", response.getId());
            return true;
        }else {
            return false;
        }
    }

    /**
     * 判断是否存在文档数据
     *
     * @param id 索引的ID
     * @return
     * @throws IOException
     */
    public boolean existIndex(Integer id) throws IOException {
        GetRequest getRequest = new GetRequest(
                EsConstant.CONTENT_INDEX,
                id.toString());
        getRequest.fetchSourceContext(new FetchSourceContext(false));
        getRequest.storedFields("_none_");
        return restHighLevelClient.exists(getRequest, COMMON_OPTIONS);
    }


    /**
     * 删除文档数据
     * @param id
     * @return
     * @throws IOException
     */
    public boolean deleteIndex(Integer id) throws IOException {
        DeleteRequest request = new DeleteRequest(
                EsConstant.CONTENT_INDEX,
                id.toString());
        DeleteResponse deleteResponse = restHighLevelClient.delete(
                request,  COMMON_OPTIONS);
        if (deleteResponse.status().getStatus()== RestStatus.OK.getStatus()){
            return true;
        }
        return false;
    }


    /**
     * 批量删除索引
     * @param ids
     * @return 返回true 表示有错误
     * @throws IOException
     */
    public boolean deleteBatchIndex(List<Integer> ids) throws IOException {

        // 批量删除数据
        BulkRequest request = new BulkRequest();

        ids.forEach(s->{
            request.add(new DeleteRequest().index(EsConstant.CONTENT_INDEX).id(s.toString()));
        });


        BulkResponse response = restHighLevelClient.bulk(request, COMMON_OPTIONS);
        return response.hasFailures();
    }


    /**
     * 更新文档数据
     * @param contentModel
     * @return
     * @throws IOException
     */
    public boolean updateIndex(ContentModel contentModel) throws IOException {
        // 修改数据
        UpdateRequest request = new UpdateRequest();
        request.index(EsConstant.CONTENT_INDEX).id(contentModel.getContentId().toString());
        String s = JSON.toJSONString(contentModel);
        request.doc(s, XContentType.JSON);

        UpdateResponse updateResponse = restHighLevelClient.update(request, COMMON_OPTIONS);
        if (updateResponse.status().getStatus()== RestStatus.OK.getStatus()){
            return true;
        }
        return false;
    }




}