package com.srmstudios.browseproducts.ui.vendor.sales;

import com.srmstudios.browseproducts.data.room.model.VendorSales;
import com.srmstudios.browseproducts.util.interfaces.IDatabaseListOps;

import java.util.List;

public class SalesPresenter implements SalesMVP.Presenter {
    private SalesMVP.View view;
    private SalesMVP.Model model;
    private SalesAdapter adapter;

    public SalesPresenter(SalesMVP.View view, SalesMVP.Model model) {
        this.view = view;
        this.model = model;
    }


    @Override
    public void getVendorOrdersByBookingDate(String bookingDate,String vendorEmail) {
        model.getVendorOrdersByBookingDate(bookingDate,vendorEmail, new IDatabaseListOps() {
            @Override
            public void onSuccess(List response) {
                List<VendorSales> vendorSales = response;
                setupRecyclerViewSalesAdapter(vendorSales);
            }

            @Override
            public void onError(String message, Throwable throwable) {
                view.showDialogMessage(message);
            }
        });
    }

    private void setupRecyclerViewSalesAdapter(List<VendorSales> vendorSales){
        adapter = new SalesAdapter(vendorSales);
        view.setRecyclerViewSalesAdapter(adapter);

        double totalDayEndSales = 0;
        for(VendorSales vendorSale : vendorSales){
            totalDayEndSales += vendorSale.getTotalOrderAmount();
        }
        view.setTxtTotalDayEndSales(totalDayEndSales);
        view.setTxtTotalOrdersReceived(vendorSales.size());
    }
}















