package com.sky.rest.framework.demo;

import com.sky.rest.framework.annotation.request.GetMapping;
import com.sky.rest.framework.annotation.request.RequestMapping;

@RequestMapping("/hello")
public class HelloController {

    @GetMapping("/index")
    public void index(Person p,String hello){

    }

}

class Person{
    String name;
    int age;
}
