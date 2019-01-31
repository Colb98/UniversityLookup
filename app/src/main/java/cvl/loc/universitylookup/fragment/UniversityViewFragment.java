package cvl.loc.universitylookup.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import cvl.loc.universitylookup.R;
import cvl.loc.universitylookup.helper.DownloadImageTask;
import cvl.loc.universitylookup.model.University;

public class UniversityViewFragment extends Fragment {
    List<University> universities;
    UniversityAdapter universityAdapter;
    RecyclerView mRecyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        final DatabaseReference uniRef = db.getReference("university");
        uniRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot uniSnapshot : dataSnapshot.getChildren()){
                    University university = uniSnapshot.getValue(University.class);
                    universities.add(university);
                    universityAdapter.notifyDataSetChanged();
//                    synchronized (universityAdapter) {
//                        universityAdapter.notify();
//                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_universities, container, false);
        //return super.onCreateView(inflater, container, savedInstanceState);
        mRecyclerView = view.findViewById(R.id.uni_recycler_view);

        universities = new ArrayList<>();
        universityAdapter = new UniversityAdapter(universities);
        mRecyclerView.setAdapter(universityAdapter);

        return view;
    }

    class UniversityAdapter extends RecyclerView.Adapter<UniversityAdapter.UniversityViewHolder> {
        List<University> universities;

        @NonNull
        @Override
        public UniversityViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(getContext()).inflate(R.layout.university_view, viewGroup, false);
            UniversityViewHolder vh = new UniversityViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(@NonNull UniversityViewHolder universityViewHolder, int i) {
            universityViewHolder.universityName.setText(universities.get(i).getName());
            universityViewHolder.universityAddr.setText(universities.get(i).getAddr());
            new DownloadImageTask(universityViewHolder.universityImage).execute(universities.get(i).getImageUri());
        }

        @Override
        public int getItemCount() {
            return universities.size();
        }

        public UniversityAdapter(List<University> list){
            universities = list;
        }

        class UniversityViewHolder extends RecyclerView.ViewHolder{
            ImageView universityImage;
            TextView universityName;
            TextView universityAddr;
            LinearLayout infoContainer;

            public UniversityViewHolder(@NonNull View itemView) {
                super(itemView);

                universityImage = itemView.findViewById(R.id.uni_image);
                universityName = itemView.findViewById(R.id.uni_name);
                universityAddr = itemView.findViewById(R.id.uni_addr);
                infoContainer = itemView.findViewById(R.id.uni_info_container);
            }
        }
    }
}
