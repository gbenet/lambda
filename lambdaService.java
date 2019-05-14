package com.lambda.service;

import com.lambda.domian.Person;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

/**
 * @Copyright: Copyright (c) 2019
 * @Company: www.baosight.com
 * @program: bfcp
 * @FileName: lambdaService
 * @description: lambda表达式使用案例
 * @author: binzhang
 * @create: 2019/05/13 17:24
 * @version:
 */
public  class  lambdaService{
    private static final Logger logger = Logger.getLogger(lambdaService.class);

    public static void main(String[] args) {
        List <Person> javaProgrammers = new ArrayList <>();
        List <Person> phpProgrammers = new ArrayList <>();
        javaProgrammers.add(new Person("Elsdon", "Jaycob", "Java programmer", "male", 43, 2000));
        javaProgrammers.add(new Person("Tamsen", "Brittany", "Java programmer", "female", 23, 1500));
        javaProgrammers.add(new Person("Floyd", "Donny", "Java programmer", "male", 33, 1800));
        javaProgrammers.add(new Person("Sindy", "Jonie", "Java programmer", "female", 32, 1600));
        javaProgrammers.add(new Person("Vere", "Hervey", "Java programmer", "male", 22, 1200));
        javaProgrammers.add(new Person("Maude", "Jaimie", "Java programmer", "female", 27, 1900));
        javaProgrammers.add(new Person("Shawn", "Randall", "Java programmer", "male", 30, 2300));
        javaProgrammers.add(new Person("Jayden", "Corrina", "Java programmer", "female", 35, 1700));
        javaProgrammers.add(new Person("Palmer", "Dene", "Java programmer", "male", 33, 2000));
        javaProgrammers.add(new Person("Addison", "Pam", "Java programmer", "female", 34, 1300));
        phpProgrammers.add(new Person("Jarrod", "Pace", "PHP programmer", "male", 34, 1550));
        phpProgrammers.add(new Person("Clarette", "Cicely", "PHP programmer", "female", 23, 1200));
        phpProgrammers.add(new Person("Victor", "Channing", "PHP programmer", "male", 32, 1600));
        phpProgrammers.add(new Person("Tori", "Sheryl", "PHP programmer", "female", 21, 1000));
        phpProgrammers.add(new Person("Osborne", "Shad", "PHP programmer", "male", 32, 1100));
        phpProgrammers.add(new Person("Rosalind", "Layla", "PHP programmer", "female", 25, 1300));
        phpProgrammers.add(new Person("Fraser", "Hewie", "PHP programmer", "male", 36, 1100));
        phpProgrammers.add(new Person("Quinn", "Tamara", "PHP programmer", "female", 21, 1000));
        phpProgrammers.add(new Person("Alvin", "Lance", "PHP programmer", "male", 38, 1600));
        phpProgrammers.add(new Person("Evonne", "Shari", "PHP programmer", "female", 40, 1800));

        logger.info("循环打印出所有程序员姓名:");
        javaProgrammers.forEach((p) -> System.out.println( p.getFirstName()+ ""+p.getLastName()));
        phpProgrammers.forEach((p) -> System.out.println(p.getFirstName()+""+ p.getLastName()));

        logger.info("将所有程序员的工资增加5%:");
        Consumer <Person> giveRaise = e -> e.setSalary(e.getSalary() / 100 * 5 + e.getSalary());
        javaProgrammers.forEach(giveRaise);
        phpProgrammers.forEach(giveRaise);
        for (Person phpProgrammer : phpProgrammers) {
            System.out.println(phpProgrammer.getSalary());
        }

        logger.info("循环取出php行业的工资大于1400的员工:");
        phpProgrammers.stream()
                .filter((p) -> (p.getSalary() > 1400))
                .forEach((p) -> System.out.println(p.getFirstName()+""+ p.getLastName()));

        logger.info("找出年龄大于30的员工:");
        javaProgrammers.stream()
                .filter((p)-> (p.getAge() > 30))
                .forEach((p)-> System.out.println(p.getFirstName()+""+p.getLastName()));

        logger.info("通过自定义的filter过滤出年龄大于35的java员工");
        //定义lambda的filter
        Predicate<Person> ageFilter = p -> p.getAge()>35;
        javaProgrammers.stream().filter(ageFilter).forEach(p -> System.out.println(p.getAge()));

        logger.info("根据name对java员工排序,并显示前五");
        javaProgrammers
                .stream()
                .sorted((p, p2) -> (p.getFirstName().compareTo(p2.getFirstName())))
                .limit(5)
                .collect(toList()).forEach(p-> System.out.println(p.getFirstName()));

        logger.info("根据age对java员工排序,并显示前五");
        //集合直接转成排序后的集合在循环输出
        javaProgrammers.stream()
                //变量前后位置一直就是升序,不一致降序
                .sorted((p1,p)->(p1.getAge()-(p.getAge())))
                .collect(toList()).forEach(p-> System.out.println(p.getAge()));

        //将集合装成需要的排序集合之后再进行排序
        List <Person> collect = javaProgrammers
                .stream()
                .sorted((p1, p) -> (p.getAge() - p1.getAge()))
                .collect(toList());
        collect.forEach(person -> System.out.println(person.getAge()));


        logger.info("显示php集合工资最高的员工");
        Person person = phpProgrammers
                .stream()
                //最低用min
                .max((p, p1) -> (p.getSalary() - p1.getSalary()))
                .get();
        System.out.println(person.getSalary());

        logger.info("将php员工集合里面的姓氏拼接;成为String字符串");
        String collect1 = phpProgrammers
                .stream()
                .map(Person::getFirstName)
                .collect(joining(" ; "));
        System.out.println(collect1);

        logger.info("将java员工集合的名字放到set集合中,并遍历");
       javaProgrammers.stream()
                .map(Person::getLastName)
                .collect(Collectors.toSet())
                .forEach(p-> System.out.println(p));


        logger.info("获取java员工集合中所有年龄的count, min, max, sum, and average:");
        IntSummaryStatistics stat= javaProgrammers.stream().mapToInt(Person::getAge).summaryStatistics();
        System.out.println("年龄最大为:"+stat.getMax());
        System.out.println("平均年龄为:"+stat.getAverage());
        System.out.println("年龄总和:"+stat.getSum());
        System.out.println("年龄总个数:"+stat.getCount());
        System.out.println("年龄最小是:"+stat.getMin());

    }

    }
