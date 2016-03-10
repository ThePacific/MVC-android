package com.pacific.example.mvc.model;

import com.pacific.example.adapter.QuickAdapter2;
import com.pacific.example.adapter.AdapterHelper2;
import com.pacific.example.bean.MenuBean;
import com.pacific.example.mvc.view.NavigationView;
import com.pacific.example.R;
import com.pacific.mvc.FragmentMVCModel;

public class NavigationModel extends FragmentMVCModel<NavigationView> {
    private QuickAdapter2<MenuBean> quickAdapter;

    public NavigationModel(NavigationView mvcView) {
        super(mvcView);
        quickAdapter = new QuickAdapter2<MenuBean>(mvcView.getContext(), R.layout.item_menu) {
            @Override
            protected void convert(AdapterHelper2 helper, MenuBean menuBean) {
                helper.setImageResource(R.id.img_menu_icon, menuBean.getIconResId());
                helper.setText(R.id.tv_menu_desc, menuBean.getDescription());
            }
        };
    }

    public QuickAdapter2<MenuBean> getQuickAdapter() {
        return quickAdapter;
    }

    public void loadMenu() {
        quickAdapter.add(new MenuBean(R.drawable.smart_ticket, mvcView.getContext().getString(R.string.smart_ticket)));
        quickAdapter.add(new MenuBean(R.drawable.auto_lock, mvcView.getContext().getString(R.string.auto_lock)));
        quickAdapter.add(new MenuBean(R.drawable.check_device, mvcView.getContext().getString(R.string.check_device)));
        quickAdapter.add(new MenuBean(R.drawable.e_key, mvcView.getContext().getString(R.string.e_key)));
        quickAdapter.add(new MenuBean(R.drawable.helper, mvcView.getContext().getString(R.string.helper)));
        quickAdapter.add(new MenuBean(R.drawable.security_monitor, mvcView.getContext().getString(R.string.security_monitor)));
        quickAdapter.add(new MenuBean(R.drawable.check_state, mvcView.getContext().getString(R.string.check_state)));
        quickAdapter.add(new MenuBean(R.drawable.work_instruction, mvcView.getContext().getString(R.string.work_instruction)));
        quickAdapter.add(new MenuBean(R.drawable.web, mvcView.getContext().getString(R.string.web)));
        quickAdapter.add(new MenuBean(R.drawable.repair_manage, mvcView.getContext().getString(R.string.repair_manage)));
    }

    public void setViewVisible(boolean visible) {
        mvcView.setViewVisible(visible);
    }
}
