package com.warm.entity.robot;

import lombok.Data;

import java.util.Date;
import java.util.List;

//个人号那边需要传递一个  List<PersonNoEnterGroupQueryRequestInfo>对象， 需要个人号那边确定下，某个任务下对应了那几个渠道，那几个群类别。
@Data
public class PersonNoEnterGroupQueryRequestInfo {

    //个人号进群人数查询
    private Integer task_id;			//任务id列表
    private List<Integer> channel_id_list;		//渠道id列表
    private List<Integer> group_category_id_list;	//群类别id列表
    private Date start_time;		//开始时间
    private Date end_time;		//结束时间
}
