package com.srmstudios.browseproducts.ui.customer.view_products;

import com.srmstudios.browseproducts.util.interfaces.IDatabaseListOps;

public interface ViewProductsMVP {

    interface View{
        void showDialogMessage(int resourceId);
        void showDialogMessage(String message);
        void setRecyclerViewViewProductsAdapter(ViewProductsAdapter adapter);
        void openProductDetail(int productId);
    }

    interface Presenter{
        void getAllProducts();
    }

    interface Model{
        void getAllProducts(IDatabaseListOps iDatabaseListOps);
    }

}
