///**
// * @(#)MybatisInterceptor.java
// *
// * @AUTHOR : (주)인우기술
// * @VERSION : 1.0
// * @DATE : 2015/04/01
// *
// * Copyright (c) 2015 by Inwoo Tech Inc, KOREA. All Rights Reserved.
// * http://www.inwoo.co.kr
// *
// * NOTICE! This software is the confidential and proprietary information of Inwoo Tech Inc.
// * You shall not disclose such Confidential
// * Information and shall use it only in accordance with the terms
// * of the license agreement you entered into with Inwoo Tech Inc.
// *
// */
//package powerservice.core.mybatis;
//
//import java.util.List;
//import java.util.Properties;
//
//import org.apache.ibatis.executor.Executor;
//import org.apache.ibatis.mapping.BoundSql;
//import org.apache.ibatis.mapping.MappedStatement;
//import org.apache.ibatis.plugin.Interceptor;
//import org.apache.ibatis.plugin.Intercepts;
//import org.apache.ibatis.plugin.Invocation;
//import org.apache.ibatis.plugin.Plugin;
//import org.apache.ibatis.plugin.Signature;
//import org.apache.ibatis.session.ResultHandler;
//import org.apache.ibatis.session.RowBounds;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
///**
// * Copyright (c) 2015 Company Inwoo Tech Inc.
// *
// * @AUTHOR : (주)인우기술
// * @VERSION : 1.0
// * @DATE : 2015/04/01
// * @PROGRAMID : MybatisInterceptor
// * @DESCRIPTION : Mybatis Interceptors
// */
//
//@Intercepts({
//    @Signature(type=Executor.class, method="query", args={MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
//    @Signature(type=Executor.class, method="update", args={MappedStatement.class, Object.class})
//})
//public class MybatisInterceptor implements Interceptor {
//
//    private final Logger LOGGER = LoggerFactory.getLogger(MybatisInterceptor.class);
//
//    private final static String [] EXCEPT_LOG_MAP_ID_LIST = new String [] {
//            "NmsgDtlMap.getNewNmsgDtlCount", // 쪽지
//            "ConsMap.getTodayConsCount", // 상담 현황
//            "ConsMap.getThisMonthConsCount",
//            "CalbDtlMap.getTodoCalbDtlCount",
//            "BswrDmndDtlMap.getTodoConsTrctHstrCount",
//            "BswrDmndDtlMap.getConsTrctBoxChprCount", // 관리 현황
//            "UserMap.mergeChnlStatInfo", // CTI 상태
//            "CtiCrncHstrMap.insertCtiCrncDtl!selectKey", // 통화 이력
//            "CtiCrncHstrMap.insertCtiCrncDtl",
//            "CtiCrncHstrMap.updateCtiCrncDtl",
//            "CtiCrncHstrMap.insertCtiCrncHstr",
//            "CtiCrncHstrMap.mergeRecConsDtl", // 상담 통화
//            "CtiCrncHstrMap.mergeRecProdDtl", // 상품 통화
//            "CtiCrncHstrMap.mergeRecTmDtl" // TM 통화
//        };
//
//    @Override
//    public Object intercept(Invocation poInvocation) throws Throwable {
//        Object oProceed = null;
//
//        try {
//            long nSttTm = System.currentTimeMillis();
//
//            oProceed = poInvocation.proceed();
//
//            Object[] oArgs = poInvocation.getArgs();
//
//            // 특정 맵ID 로그 출력 제외
//            MappedStatement oMappedStatement = (MappedStatement) oArgs[0];
//            for (byte i = 0; i < EXCEPT_LOG_MAP_ID_LIST.length; i++) {
//                if (oMappedStatement.getId().equals(EXCEPT_LOG_MAP_ID_LIST[i])) {
//                    return oProceed;
//                }
//            }
//
//            BoundSql oBoundSql = oMappedStatement.getBoundSql(oArgs[1]);
//            StringBuilder sSql = new StringBuilder(oBoundSql.getSql());
//
//            long nJobTm = System.currentTimeMillis() - nSttTm;
//            if (nJobTm < 5000) {
//                LOGGER.info("???????????? [" + oMappedStatement.getId() + "] - (" + (nJobTm < 1000 ? (nJobTm + "ms") : (((long) (nJobTm / 1000)) + "s")) + ")");
//            } else {
//                LOGGER.info("!!!!!!!!!!!! Mybatis Search Time Warning [" +  oMappedStatement.getId() + "] - (" + (nJobTm < 1000 ? (nJobTm + "ms") : (((long) (nJobTm / 1000)) + "s")) + ") !!!!!!!!!!!!");
//                LOGGER.info("!!! [" +  oMappedStatement.getId() + "] QUERY ==>\r\n" + sSql);
//                LOGGER.info("!!! [" +  oMappedStatement.getId() + "] PARAM ==> " + oArgs[1]);
//                LOGGER.info("!!!!!!!!!!!! Mybatis Search Time Warning [" +  oMappedStatement.getId() + "] - (" + (nJobTm < 1000 ? (nJobTm + "ms") : (((long) (nJobTm / 1000)) + "s")) + ") !!!!!!!!!!!!");
//            }
//
//            if (oProceed != null && oProceed instanceof List) {
//                List<?> oList = (List<?>) oProceed;
//                if (oList != null && oList.size() > 1000) {
//                    LOGGER.info("!!!!!!!!!!!! Mybatis Search List Warning [" +  oMappedStatement.getId() + "] - (" + oList.size() + ") !!!!!!!!!!!!");
//                    LOGGER.info("!!! [" +  oMappedStatement.getId() + "] QUERY ==>\r\n" + sSql);
//                    LOGGER.info("!!! [" +  oMappedStatement.getId() + "] PARAM ==> " + oArgs[1]);
//                    LOGGER.info("!!!!!!!!!!!! Mybatis Search List Warning [" +  oMappedStatement.getId() + "] - (" + oList.size() + ") !!!!!!!!!!!!");
//                }
//            }
//        } catch (Throwable throwable) {
//            Object[] oArgs = poInvocation.getArgs();
//            MappedStatement oMappedStatement = (MappedStatement) oArgs[0];
//
//            BoundSql oBoundSql = oMappedStatement.getBoundSql(oArgs[1]);
//            StringBuilder sSql = new StringBuilder(oBoundSql.getSql());
//
//            LOGGER.error("!!!!!!!!!!!! Mybatis Query Error [" +  oMappedStatement.getId() + "] !!!!!!!!!!!!");
//            LOGGER.error("!!! [" +  oMappedStatement.getId() + "] QUERY ==>\r\n" + sSql);
//            LOGGER.error("!!! [" +  oMappedStatement.getId() + "] PARAM ==> " + oArgs[1]);
//            LOGGER.error("!!!!!!!!!!!! Mybatis Query Error [" +  oMappedStatement.getId() + "] !!!!!!!!!!!!");
//
//            throw throwable;
//        }
//
//        return oProceed;
//    }
//
//
//    @Override
//    public Object plugin(Object poTarget) {
//        return Plugin.wrap(poTarget, this);
//    }
//
//    @Override
//    public void setProperties(Properties poProperties) {
//        LOGGER.debug("MYBATIS PROPERTIES " + poProperties);
//    }
//
//}


package powerservice.core.mybatis;

import java.lang.reflect.Field;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Intercepts({
        @Signature(type = StatementHandler.class, method = "update", args = { Statement.class }),
        @Signature(type = StatementHandler.class, method = "query", args = {
                Statement.class, ResultHandler.class }) })

public class MybatisInterceptor implements Interceptor {

    private static Logger sqlLog = LoggerFactory.getLogger(MybatisInterceptor.class);

    private final static String [] EXCEPT_LOG_MAP_ID_LIST = new String [] {
            "NmsgDtlMap.getNewNmsgDtlCount", // 쪽지
            "ConsMap.getTodayConsCount", // 상담 현황
            "ConsMap.getThisMonthConsCount",
            "CalbDtlMap.getTodoCalbDtlCount",
            "BswrDmndDtlMap.getTodoConsTrctHstrCount",
            "BswrDmndDtlMap.getConsTrctBoxChprCount", // 관리 현황
            "UserMap.mergeChnlStatInfo", // CTI 상태
            "CtiCrncHstrMap.insertCtiCrncDtl!selectKey", // 통화 이력
            "CtiCrncHstrMap.insertCtiCrncDtl",
            "CtiCrncHstrMap.updateCtiCrncDtl",
            "CtiCrncHstrMap.insertCtiCrncHstr",
            "CtiCrncHstrMap.mergeRecConsDtl", // 상담 통화
            "CtiCrncHstrMap.mergeRecProdDtl", // 상품 통화
            "CtiCrncHstrMap.mergeRecTmDtl" // TM 통화
        };

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object oRet = null;

        StatementHandler handler = (StatementHandler) invocation.getTarget();


        boolean printLog = true;

        // 특정 맵ID 로그 출력 제외
//		Object[] oArgs = invocation.getArgs();
//        MappedStatement oMappedStatement = (MappedStatement) oArgs[0];
//        for (byte i = 0; i < EXCEPT_LOG_MAP_ID_LIST.length; i++) {
//            if (oMappedStatement.getId().equals(EXCEPT_LOG_MAP_ID_LIST[i])) {
//            	printLog = false;
//                break;
//            }
//        }

        if (printLog) {
            String sql = bindSql(handler); // SQL 추출
            sqlLog.info("=====================================================================");
            sqlLog.info("{} ", sql);
            sqlLog.info("=====================================================================");
        }

        oRet = invocation.proceed();
        return oRet;

    }

    /**
     * <pre>
     * bindSql
     *
     * <pre>
     *
     * @param boundSql
     * @param sql
     * @param param
     * @return
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    @SuppressWarnings("rawtypes")
    private String bindSql(StatementHandler handler)
            throws NoSuchFieldException, IllegalAccessException {

        BoundSql boundSql = handler.getBoundSql();

        // 쿼리실행시 맵핑되는 파라미터를 구한다
        Object param = handler.getParameterHandler().getParameterObject();
        // 쿼리문을 가져온다(이 상태에서의 쿼리는 값이 들어갈 부분에 ?가 있다)
        String sql = boundSql.getSql();

        // 바인딩 파라미터가 없으면
        if (param == null) {
            sql = sql.replaceFirst("\\?", "''");
            return sql;
        }

        // 해당 파라미터의 클래스가 Integer, Long, Float, Double 클래스일 경우
        if (param instanceof Integer || param instanceof Long
                || param instanceof Float || param instanceof Double) {
            sql = sql.replaceFirst("\\?", param.toString());
        }
        // 해당 파라미터의 클래스가 String인 경우
        else if (param instanceof String) {
            sql = sql.replaceFirst("\\?", "'" + param + "'");
        }
        // 해당 파라미터의 클래스가 Map인 경우
        else if (param instanceof Map) {

            List<ParameterMapping> paramMapping = boundSql.getParameterMappings();

            for (ParameterMapping mapping : paramMapping) {
                String propValue = mapping.getProperty();

                if ( propValue == null || propValue.startsWith("__frch_item_")) {
                    continue;
                }

                // Object value = ((Map) param).get(propValue);
                Object value = ((Map) param).get(propValue);

                if (value == null) {
                    continue;
                }

                if (value instanceof String) {
                    sql = sql.replaceFirst("\\?", "'" + value + "'");
                } else {
                    sql = sql.replaceFirst("\\?", value.toString());
                }
            }
        }
        // 해당 파라미터의 클래스가 사용자 정의 클래스인 경우
        else {
            List<ParameterMapping> paramMapping = boundSql
                    .getParameterMappings();
            Class<? extends Object> paramClass = param.getClass();

            for (ParameterMapping mapping : paramMapping) {
                String propValue = mapping.getProperty();
                Field field = paramClass.getDeclaredField(propValue);
                field.setAccessible(true);
                Class<?> javaType = mapping.getJavaType();
                if (String.class == javaType) {
                    sql = sql.replaceFirst("\\?", "'" + field.get(param) + "'");
                } else {
                    sql = sql.replaceFirst("\\?", field.get(param).toString());
                }
            }
        }

        return sql;
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
    }
}
