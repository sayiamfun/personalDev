package com.warm.entity.robot;

import lombok.Data;

//返回给个人号一个  List<PersonNoEnterGroupQueryResponseInfo>对象， 那个任务，在指定的通道下，进了多少人
public class PersonNoEnterGroupQueryResponseInfo {

    public Integer task_id;			//任务id列表
    public Integer enter_group_group_member_count;	//进群人数
    public Integer enter_group_group_member_count_resverd;	//进群人数
}
