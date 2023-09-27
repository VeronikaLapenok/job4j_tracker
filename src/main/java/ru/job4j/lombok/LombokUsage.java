package ru.job4j.lombok;

public class LombokUsage {
    public static void main(String[] args) {
        Permission permission = Permission.of()
                .id(1)
                .name("Name")
                .accessBy("create")
                .accessBy("update")
                .accessBy("delete")
                .build();
        System.out.println(permission);
    }
}
