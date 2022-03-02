package com.example.hiui.refresh;

public interface HiRefresh {

    void setDisableRefreshScroll(boolean disableRefreshScroll);

    void refreshFinished();

    void setRefreshListener(HiRefresh.HiRefreshListen hiRefreshListen);

    void setRefreshOverView(HiOverView hiOverView);

    interface HiRefreshListen {
        void onRefresh();

        boolean enableRefresh();
    }


}
