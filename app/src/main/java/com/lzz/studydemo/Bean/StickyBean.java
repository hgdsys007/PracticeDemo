package com.lzz.studydemo.Bean;

public class StickyBean extends BaseBean{

    public String name;
    public String autor;
    public String sticky;

    public StickyBean(String sticky, String name, String autor) {
        this.sticky = sticky;
        this.name = name;
        this.autor = autor;
    }
}