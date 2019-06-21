package com.srmstudios.browseproducts.ui.useful_resources;

import com.srmstudios.browseproducts.data.room.model.Repo;
import com.srmstudios.browseproducts.util.interfaces.IDatabaseListOps;
import com.srmstudios.browseproducts.util.interfaces.IDatabaseOps;

public interface UsefulResourcesMVP {

    interface View{
        void showDialogMessage(int resourceId);
        void showDialogMessage(String message);
        void setRecyclerViewUsefulResourcesAdapter(UsefulResourcesAdapter adapter);
        void showRecyclerViewUsefulResources();
        void showTxtNoDataFound();
        void openLink(String url);
        void openPDF(String localStoragePath);
    }

    interface Presenter{
        void addItemInList(String itemType,String name,String url);
        void getRepoList();
    }

    interface Model{
        void addRepoItem(Repo repo, IDatabaseOps iDatabaseOps);
        void getRepoList(IDatabaseListOps iDatabaseListOps);
    }
}
