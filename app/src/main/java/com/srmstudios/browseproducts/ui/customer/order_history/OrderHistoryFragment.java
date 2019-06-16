package com.srmstudios.browseproducts.ui.customer.order_history;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.srmstudios.browseproducts.R;
import com.srmstudios.browseproducts.util.DialogUtils;
import com.srmstudios.browseproducts.util.Utils;
import com.srmstudios.browseproducts.util.singleton.BrowseProductsDatabase;
import com.srmstudios.browseproducts.util.singleton.SessionManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderHistoryFragment extends Fragment implements OrderHistoryMVP.View {
    @BindView(R.id.recyclerViewOrderHistory)
    protected RecyclerView recyclerViewOrderHistory;
    @BindView(R.id.txtNoDateFound)
    protected TextView txtNoDateFound;

    private Unbinder unbinder;
    private OrderHistoryPresenter presenter;

    public OrderHistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new OrderHistoryPresenter(this,
                new OrderHistoryModel(BrowseProductsDatabase.getInstance(getContext()).getAppDatabase()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_order_history, container, false);

        initializeViews(v);

        return v;
    }

    private void initializeViews(View v){
        unbinder = ButterKnife.bind(this,v);

        recyclerViewOrderHistory.setLayoutManager(new LinearLayoutManager(getContext()));

        presenter.getCustomerOrderHistory(SessionManager.getInstance(getContext()).getUser().getEmail());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void showDialogMessage(int resourceId) {
        DialogUtils.showSingleButtonDialog(getContext(),
                Utils.getStringFromResourceId(getContext(),R.string.alert),
                Utils.getStringFromResourceId(getContext(),resourceId));
    }

    @Override
    public void showDialogMessage(String message) {
        DialogUtils.showSingleButtonDialog(getContext(),
                Utils.getStringFromResourceId(getContext(),R.string.alert),
                message);
    }

    @Override
    public void setRecyclerViewOrderHistoryAdapter(OrderHistoryAdapter adapter) {
        if(adapter == null){
            return;
        }
        if(adapter.getItemCount() == 0){
            txtNoDateFound.setVisibility(View.VISIBLE);
            txtNoDateFound.setText(Utils.getStringFromResourceId(getContext(),R.string.no_orders_found));
        }else {
            recyclerViewOrderHistory.setAdapter(adapter);
        }
    }

}
