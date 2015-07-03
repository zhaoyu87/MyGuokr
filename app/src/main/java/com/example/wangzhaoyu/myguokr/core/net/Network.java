package com.example.wangzhaoyu.myguokr.core.net;

import com.android.volley.Request;

/**
 * 网络请求api
 *
 * @author wangzhaoyu
 */
public class Network {
    //根节点
    public static final String ROOT_URL = "www.guokr.com/apis/";

    /**
     * HttpHeader信息配置类
     */
    public final class HttpHeader {
        public final class KEY {
            public static final String CLIENT_SOURCE = "client-source";
            public static final String AUTHORIZATION = "authorization";
        }

        public final class VALUE {
            public static final String CLIENT_SOURCE = "android";
        }
    }

    /**
     * 变量命名规则：
     */
    public final class API {
        // 科学人article
        public static final String MINISITE_ARTICLE = "minisite/article.json";
    }

    /**
     * 参数
     */
    public final class Parameters {
        public static final String RETRIEVE_TYPE = "retrieve_type";
        public static final String LIMIT = "limit";
        public static final String OFFSET = "offset";
        public static final String SUBJECT_KEY = "subject_key";
        public static final String CHANNEL_KEY = "channel_key";

        public final class RetrieveType {
            public static final String BY_SUBJECT = "by_subject";
            public static final String BY_CHANNEL = "by_channel";
        }
    }

    /**
     * http协议类型
     */
    public final class Protocol {
        public static final String HTTP = "http://";
        public static final String HTTPS = "https://";
    }

    /**
     * http方法
     */
    public final class HttpMethod {
        public static final int GET = Request.Method.GET;
        public static final int POST = Request.Method.POST;
        public static final int PUT = Request.Method.PUT;
        public static final int DELETE = Request.Method.DELETE;
        public static final int HEAD = Request.Method.HEAD;
    }

    /**
     * 根据给定协议返回当前api root url
     *
     * @param protocol 协议
     * @return root url
     */
    public static String getAPIRootUrl(String protocol) {
        String rootUrl = ROOT_URL;
        return protocol + rootUrl;
    }

    /**
     * 获取root url
     *
     * @return root url
     */
    public static String getAPIRootUrl() {
        return getAPIRootUrl(Protocol.HTTP);
    }
}