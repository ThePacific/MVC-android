package com.pacific.example.mvc.model;

import com.pacific.adapter.RecyclerAdapter;
import com.pacific.adapter.RecyclerAdapterHelper;
import com.pacific.example.bean.MenuBean;
import com.pacific.example.mvc.view.NavigationView;
import com.pacific.example.R;
import com.pacific.mvc.FragmentModel;

public class NavigationModel extends FragmentModel<NavigationView> {
    private RecyclerAdapter<MenuBean> adapter;

    public NavigationModel(NavigationView mvcView) {
        super(mvcView);
        adapter = new RecyclerAdapter<MenuBean>(view.getContext(), R.layout.item_menu) {
            @Override
            protected void convert(RecyclerAdapterHelper helper, MenuBean menuBean) {
                helper.setImageResource(R.id.img_menu_icon, menuBean.getIconResId());
                helper.setText(R.id.tv_menu_desc, menuBean.getDescription());
            }
        };
    }

    public RecyclerAdapter<MenuBean> getAdapter() {
        return adapter;
    }

    public void loadMenu() {
        adapter.add(new MenuBean(R.drawable.smart_ticket, view.getContext().getString(R.string.smart_ticket)));
        adapter.add(new MenuBean(R.drawable.auto_lock, view.getContext().getString(R.string.auto_lock)));
        adapter.add(new MenuBean(R.drawable.check_device, view.getContext().getString(R.string.check_device)));
        adapter.add(new MenuBean(R.drawable.e_key, view.getContext().getString(R.string.e_key)));
        adapter.add(new MenuBean(R.drawable.helper, view.getContext().getString(R.string.helper)));
        adapter.add(new MenuBean(R.drawable.security_monitor, view.getContext().getString(R.string.security_monitor)));
        adapter.add(new MenuBean(R.drawable.check_state, view.getContext().getString(R.string.check_state)));
        adapter.add(new MenuBean(R.drawable.work_instruction, view.getContext().getString(R.string.work_instruction)));
        adapter.add(new MenuBean(R.drawable.web, view.getContext().getString(R.string.web)));
        adapter.add(new MenuBean(R.drawable.repair_manage, view.getContext().getString(R.string.repair_manage)));
    }

    public void setViewVisible(boolean visible) {
        view.setViewVisible(visible);
    }
}
