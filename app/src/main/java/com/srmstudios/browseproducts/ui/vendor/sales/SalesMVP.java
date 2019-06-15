package com.srmstudios.browseproducts.ui.vendor.sales;

import com.srmstudios.browseproducts.util.interfaces.IDatabaseListOps;

public interface SalesMVP {

    interface View{
        void showDialogMessage(int resourceId);
        void showDialogMessage(String message);
        void setRecyclerViewSalesAdapter(SalesAdapter adapter);
        void setTxtTotalDayEndSales(double totalDayEndSales);
    }

    interface Presenter{
        void getVendorOrdersByBookingDate(String bookingDate,String vendorEmail);
    }

    interface Model{
        void getVendorOrdersByBookingDate(String bookingDate,String vendorEmail,IDatabaseListOps iDatabaseListOps);
    }

}
