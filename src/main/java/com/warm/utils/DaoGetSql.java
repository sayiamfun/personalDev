package com.warm.utils;


import java.util.Collection;

public class DaoGetSql {
    public static StringBuffer getTempSql(StringBuffer temp, boolean f) {
        if(f){
            temp.append(" and ");
        }else {
            temp.append(" where ");
        }
        return temp;
    }
    /**
     * 根据id获得一个对象
     * @param db
     * @param id
     * @return
     */
    public static String getById(String db, Integer id){
        return "select * from " + db + " where id = " + id;
    }

    /**
     * 查询所有
     * asc 正序还是倒叙
     * @param db
     * @param asc
     * @return
     */
    public static String listAll(String db, String asc){
        if("desc".equals(asc)){
            return "select * from " + db + " order by id desc";
        }
        return "select * from " + db + " order by id asc";
    }

    /**
     * 根据id删除
     * @param db
     * @param id
     * @return
     */
    public static String deleteById(String db, Integer id){
        return "delete from " + db + " where id = " + id;
    }

    /**
     * 生成sql语句
     * @param sql
     * @param params
     * @return
     */
    public static String getSql(String sql, Object...params){
        boolean b = veryNumOfParams(sql, params);
        if(!b){
            throw new RuntimeException("sql语句和参数个数不匹配");
        }
        StringBuffer temsql = new StringBuffer(sql);
        for (Object param : params) {
            int i = temsql.indexOf("?");
            temsql.deleteCharAt(i);
            if(param instanceof String){
                temsql.insert(i,"'" + param + "'");
            }else{
                temsql.insert(i,param);
            }
        }
        return temsql.toString();
    }

    /**
     * 检验语句需要参数个数和传入参数个数是否统一
     * @param sql
     * @param params
     * @return
     */
    private static boolean veryNumOfParams(String sql, Object...params){
        if(getNum(sql)>0){
            if(null == params || getNum(sql) != params.length){
                return false;
            }
        }else {
            if (null != params && params.length > 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * 将id集合转换成字符串
     * @param list
     * @return
     */
    public static String getIds(Collection<?> list){
        if(VerifyUtils.collectionIsEmpty(list)){
            return "(-1)";
        }
        StringBuffer temp = new StringBuffer();
        temp.append("(");
        boolean b = false;
        for (Object integer : list) {
            if(b){
                temp.append(",");
            }
            if(integer instanceof String){
                temp.append("'"+integer+"'");
            }else {
                temp.append(integer);
            }
            b = true;
        }
        temp.append(")");
        return temp.toString();
    }

    /**
     * 得到sql语句需要的参数个数
     * @param sql
     * @return
     */
    private static int getNum(String sql) {
        int fromIndex = 0;
        int count = 0;
        while(true){
            int index = sql.indexOf("?", fromIndex);
            if(-1 != index){
                fromIndex = index + 1;
                count++;
            }else{
                break;
            }
        }
        return count;
    }

    public static void main(String[] args) {
        String selectSql = DaoGetSql.listAll("user", "desc");
        String string = DaoGetSql.getSql("字符串 ?", "123");
        String num = DaoGetSql.getSql("数字 ?", 1);
        System.err.println(selectSql);
        System.err.println(string);
        System.err.println(num);
    }
}
