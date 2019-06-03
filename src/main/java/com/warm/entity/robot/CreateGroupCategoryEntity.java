package com.warm.entity.robot;


import java.util.ArrayList;
import java.util.List;

public class CreateGroupCategoryEntity {
    public int group_category_set_id;     //集合id
    public List<String> assistantList = new ArrayList<>();   //小助手 昵称列表
    public GroupCategory group_category;   //
    public Integer empty_group_count;
}
