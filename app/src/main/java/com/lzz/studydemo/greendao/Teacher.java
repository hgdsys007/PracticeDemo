package com.lzz.studydemo.greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class Teacher {
    @Id(autoincrement = true)
    private Long id;
    private String name;

    @Generated(hash = 1434396195)
    public Teacher(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Generated(hash = 1630413260)
    public Teacher() {
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
