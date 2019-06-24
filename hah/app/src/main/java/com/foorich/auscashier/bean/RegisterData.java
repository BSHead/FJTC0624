package com.foorich.auscashier.bean;

import java.util.List;

/**
 * author : YangDngHai
 * e-mail : 18201097621@163.com
 * date   : 2019-3-12 15:22
 * desc   :
 * version: 1.0
 */
public class RegisterData {

    private boolean isError;
    private List<RegisterBean> results;

    public boolean isError() {
        return isError;
    }

    public void setError(boolean error) {
        isError = error;
    }

    public List<RegisterBean> getResults() {
        return results;
    }

    public void setResults(List<RegisterBean> results) {
        this.results = results;
    }


}
