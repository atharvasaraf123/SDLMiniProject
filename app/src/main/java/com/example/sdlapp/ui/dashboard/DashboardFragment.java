package com.example.sdlapp.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sdlapp.Club;
import com.example.sdlapp.R;
import com.example.sdlapp.ui.browseclub.RecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {

    private DashboardViewModel homeViewModel;
    ArrayList<String> list;
    FirebaseFirestore firestore;
    List<String> list1 = new ArrayList<>();
    List<Club> lis = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

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
}