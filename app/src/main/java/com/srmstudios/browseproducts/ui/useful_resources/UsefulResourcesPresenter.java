package com.srmstudios.browseproducts.ui.useful_resources;

import com.srmstudios.browseproducts.R;
import com.srmstudios.browseproducts.data.room.model.Product;
import com.srmstudios.browseproducts.data.room.model.Repo;
import com.srmstudios.browseproducts.util.AppConstants;
import com.srmstudios.browseproducts.util.interfaces.IDatabaseListOps;
import com.srmstudios.browseproducts.util.interfaces.IDatabaseOps;

import java.util.ArrayList;
import java.util.List;

public class UsefulResourcesPresenter implements UsefulResourcesMVP.Presenter, UsefulResourcesAdapter.IUsefulResourcesAdapter {
    private UsefulResourcesMVP.View view;
    private UsefulResourcesMVP.Model model;
    private UsefulResourcesAdapter adapter;

    public UsefulResourcesPresenter(UsefulResourcesMVP.View view, UsefulResourcesMVP.Model model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void onItemClick(Repo repo) {
        if(repo.getType().equals(AppConstants.REPO_TYPE_LINK)) {
            view.openLink(repo.getUrl());
        }else {
            view.openPDF(repo.getUrl());
        }
    }

    @Override
    public void addItemInList(String itemType,String name,String url) {
        Repo newRepo = new Repo();
        newRepo.setType(itemType);
        newRepo.setUrl(url);
        newRepo.setName(name);
        model.addRepoItem(newRepo, new IDatabaseOps() {
            @Override
            public void onSuccess(Object response) {
                if(response instanceof String){
                    view.showDialogMessage(response.toString());
                }else if(response instanceof Repo){
                    Repo repo = (Repo) response;
                    view.showDialogMessage(R.string.resource_added_successfully);
                    List<Repo> repoList = new ArrayList<>();
                    repoList.add(repo);
                    setupRecyclerViewUsefulResourcesAdapter(repoList);
                }
            }

            @Override
            public void onError(String message, Throwable throwable) {
                view.showDialogMessage(message);
            }
        });
    }

    @Override
    public void getRepoList() {
        model.getRepoList(new IDatabaseListOps() {
            @Override
            public void onSuccess(List response) {
                List<Repo> repoList = response;
                setupRecyclerViewUsefulResourcesAdapter(repoList);
            }

            @Override
            public void onError(String message, Throwable throwable) {
                view.showDialogMessage(message);
            }
        });
    }

    private void setupRecyclerViewUsefulResourcesAdapter(List<Repo> repoList){
        if(repoList == null){
            view.showTxtNoDataFound();
        }else if(repoList.size() == 0){
            view.showTxtNoDataFound();
        }else {
            view.showRecyclerViewUsefulResources();
            if(adapter == null){
                adapter = new UsefulResourcesAdapter(repoList, this);
                view.setRecyclerViewUsefulResourcesAdapter(adapter);
            }else {
                adapter.addResource(repoList.get(0));
            }
        }
    }
}








