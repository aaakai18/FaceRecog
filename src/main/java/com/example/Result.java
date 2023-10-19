package com.example;

public class Result {

        public Integer code;
        public Object data;
        public String msg;

        public Result() {
        }

        public Result(Integer code, Object data) {
            this.code = code;
            this.data = data;
        }

        public Result(Integer code, Object data, String msg) {
            this.code = code;
            this.data = data;
            this.msg = msg;
        }

}
