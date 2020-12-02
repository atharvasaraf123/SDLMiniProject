package com.example.sdlapp.ui.browseevent;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.sdlapp.Event;
import com.example.sdlapp.EventSuggestion;
import com.example.sdlapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BrowseEventFragment extends Fragment {

    private BrowseEventViewModel slideshowViewModel;
    SearchView searchView;
    ListView listView;
    ArrayList<String> list;
    ArrayAdapter<String> adapter;
    FirebaseFirestore firestore;
    List<String> list1 = new ArrayList<>();
    List<Event> lis = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                new ViewModelProvider(this).get(BrowseEventViewModel.class);
        View root = inflater.inflate(R.layout.fragment_browse, container, false);
        searchView = root.findViewById(R.id.searchView);
        listView = root.findViewById(R.id.lv1);
        list = new ArrayList<>();
        firestore = FirebaseFirestore.getInstance();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("abcd", list1.get(i));
                Intent intent = new Intent(getContext(), EventSuggestion.class);
                for (int j = 0; j < lis.size(); j++) {
                    if (lis.get(j).getTitle().equals(list1.get(i))) {
                        intent.putExtra("values", lis.get(j));
                        break;
                    }
                }
                startActivity(intent);
            }
        });
//        firestore.collection("clubs").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if (task.isSuccessful()) {
//                    List<DocumentSnapshot> docSnam = task.getResult().getDocuments();
//                    for (int i = 0; i < docSnam.size(); i++) {
//                        Map<String, Object> mapp = docSnam.get(i).getData();
//                        String tit = mapp.get("title").toString();
//                        Log.d("wtf", tit);
//                        list.add(tit);
//                        list1.add(tit);
//                        Event event = new Event(tit, mapp.get("desc").toString(), mapp.get("date").toString(), mapp.get("time").toString(), mapp.get("venue").toString(), (HashMap<String, Boolean>) mapp.get("dept"), mapp.get("uid").toString(), mapp.get("eventID").toString());
//                        lis.add(event);
//                    }
//                    adapter = new ArrayAdapter<String>(getContext(), R.layout.suggestion_layout, list);
//                    listView.setAdapter(adapter);
//                    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//                        @Override
//                        public boolean onQueryTextSubmit(String query) {
//
//                            if (list.contains(query)) {
//                                adapter.getFilter().filter(query);
//                            }//                                Toast.makeText(getContext(), "No Match found",Toast.LENGTH_LONG).show();
//
//                            return false;
//                        }
//
//                        @Override
//                        public boolean onQueryTextChange(String newText) {
//                            list1 = new ArrayList<>();
//                            for (String s : list) {
//                                if (s.contains(newText)) {
//                                    list1.add(s);
//                                }
//                            }
//                            if (list1.isEmpty()) {
//                                Toast.makeText(getContext(), "No Match found", Toast.LENGTH_LONG).show();
//                            }
//                            adapter = new ArrayAdapter<String>(getContext(), R.layout.suggestion_layout, list1);
//                            listView.setAdapter(adapter);
//                            //    adapter.getFilter().filter(newText);
//                            return false;
//                        }
//                    });
//                }
//            }
//        });
        firestore.collection("clubs").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(int i=0;i<queryDocumentSnapshots.getDocuments().size();i++){
                    boolean o=queryDocumentSnapshots.getDocuments().get(0).contains("events");
                    Log.d("abc", String.valueOf(o));
                }
            }
        });
        return root;
    }

}