package com.smallfinance.enums;

import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.HttpMethod;

public enum RequestMethod {
    GET, HEAD, POST, PUT, PATCH, DELETE, OPTIONS, TRACE;

    private RequestMethod() {
    }

    @Nullable
    public static RequestMethod resolve(String method) {
        if (method==null)
            throw new IllegalArgumentException(("HttpMethod must not be null"));
        RequestMethod var10000;
        switch (method) {
            case "GET":
                var10000 = GET;
                break;
            case "HEAD":
                var10000 = HEAD;
                break;
            case "POST":
                var10000 = POST;
                break;
            case "PUT":
                var10000 = PUT;
                break;
            case "PATCH":
                var10000 = PATCH;
                break;
            case "DELETE":
                var10000 = DELETE;
                break;
            case "OPTIONS":
                var10000 = OPTIONS;
                break;
            case "TRACE":
                var10000 = TRACE;
                break;
            default:
                var10000 = null;
        }

        return var10000;
    }

        @Nullable
    public static RequestMethod resolve(HttpMethod httpMethod) {
            if (httpMethod==null)
                throw new IllegalArgumentException(("HttpMethod must not be null"));
        return resolve(httpMethod.name());
    }
        public HttpMethod asHttpMethod () {
            HttpMethod var10000;
            switch (this) {
                case GET:
                    var10000 = HttpMethod.GET;
                    break;
                case HEAD:
                    var10000 = HttpMethod.HEAD;
                    break;
                case POST:
                    var10000 = HttpMethod.POST;
                    break;
                case PUT:
                    var10000 = HttpMethod.PUT;
                    break;
                case PATCH:
                    var10000 = HttpMethod.PATCH;
                    break;
                case DELETE:
                    var10000 = HttpMethod.DELETE;
                    break;
                case OPTIONS:
                    var10000 = HttpMethod.OPTIONS;
                    break;
                case TRACE:
                    var10000 = HttpMethod.TRACE;
                    break;
                default:
                    throw new IncompatibleClassChangeError();
            }

            return var10000;
        }
    }
