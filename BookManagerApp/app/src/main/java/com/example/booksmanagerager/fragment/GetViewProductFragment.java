package com.example.booksmanagerager.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.booksmanagerager.AddProductActivity;
import com.example.booksmanagerager.HomeMainActivity;
import com.example.booksmanagerager.R;
import com.example.booksmanagerager.adapter.SanPhamAdapter;
import com.example.booksmanagerager.model.SanPham;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class GetViewProductFragment extends Fragment {

    Button btn_add;

    RecyclerView recyclerViewAllProduct;
    SanPhamAdapter sanPhamAdapter;
    ArrayList<SanPham> list;
    FirebaseFirestore database;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_get_view_product, container, false);
        recyclerViewAllProduct = view.findViewById(R.id.recyclerview);
        btn_add = view.findViewById(R.id.btn_addproduct);

        list = new ArrayList<>();
        sanPhamAdapter = new SanPhamAdapter(getContext(), list, database);
        recyclerViewAllProduct.setAdapter(sanPhamAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerViewAllProduct.setLayoutManager(linearLayoutManager);



        database = FirebaseFirestore.getInstance();
        ListenFirebaseFirestore();

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddProductActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    private void ListenFirebaseFirestore(){
        database.collection("SANPHAM").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error != null){
                    Log.e("TAG", "Listen failted", error);
                    return;
                }
                if (value != null){
                    for (DocumentChange documentChange : value.getDocumentChanges()){
                        switch (documentChange.getType()){
                            case ADDED:
                                documentChange.getDocument().toObject(SanPham.class);
                                list.add(documentChange.getDocument().toObject(SanPham.class));
                                sanPhamAdapter.notifyItemInserted(list.size() - 1);
                                break;
                            case MODIFIED:
                                SanPham updateSanPham = documentChange.getDocument().toObject(SanPham.class);

                                if (documentChange.getOldIndex() == documentChange.getNewIndex()){

                                    list.set(documentChange.getOldIndex(), updateSanPham);

                                    sanPhamAdapter.notifyItemChanged(documentChange.getOldIndex());
                                }else {

                                    list.remove(documentChange.getOldIndex());

                                    list.add(updateSanPham);
                                    sanPhamAdapter.notifyItemMoved(documentChange.getOldIndex(), documentChange.getNewIndex());
                                }
                                break;
                            case REMOVED:
                                documentChange.getDocument().toObject(SanPham.class);
                                list.remove(documentChange.getOldIndex());
                                sanPhamAdapter.notifyItemRemoved(documentChange.getOldIndex());
                                break;
                        }
                    }
                }
            }
        });
    }
}