    package com.example.sdlapp.ui.browseclub;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.sdlapp.Club;
import com.example.sdlapp.Event;
import com.example.sdlapp.EventSuggestion;
import com.example.sdlapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

    public class Browseclub extends Fragment {

        private BrowseclubViewModel mViewModel;
        ArrayList<String> list;
        FirebaseFirestore firestore;
        List<String> list1 = new ArrayList<>();
        List<Club> lis = new ArrayList<>();

        public static Browseclub newInstance() {
            return new Browseclub();
        }

        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                                 @Nullable Bundle savedInstanceState) {
            View root = inflater.inflate(R.layout.browseclub_fragment, container, false);
//            searchView = root.findViewById(R.id.searchView);
//            listView = root.findViewById(R.id.lv1);
            list = new ArrayList<>();
            final QuerySnapshot[] querySnapshot = new QuerySnapshot[1];
            RecyclerView recyclerView=root.findViewById(R.id.recyclerView1);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            firestore = FirebaseFirestore.getInstance();
//            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                    Log.d("abcd", list1.get(i));
//                    Intent intent = new Intent(getContext(), EventSuggestion.class);
//                    for (int j = 0; j < lis.size(); j++) {
//                        if (lis.get(j).getTitle().equals(list1.get(i))) {
//                            intent.putExtra("values", lis.get(j));
//                            break;
//                        }
//                    }
//                    startActivity(intent);
//                }
//            });
            firestore.collection("clubs").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        querySnapshot[0] =task.getResult();
                        recyclerView.setAdapter(new RecyclerAdapter(querySnapshot[0]));
                    }
                }
            });

            return root;
        }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(BrowseclubViewModel.class);

        // TODO: Use the ViewModel
    }

}