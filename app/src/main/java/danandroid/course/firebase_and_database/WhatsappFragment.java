package danandroid.course.firebase_and_database;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 */
public class WhatsappFragment extends Fragment {
    FirebaseDatabase mDatabase;
    FirebaseUser user;

    @BindView(R.id.etMessage)
    EditText etMessage;
    @BindView(R.id.btnSend)
    Button btnSend;
    @BindView(R.id.rvChat)
    RecyclerView rvChat;
    Unbinder unbinder;

    public WhatsappFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_whatsapp, container, false);
        unbinder = ButterKnife.bind(this, view);

        mDatabase = FirebaseDatabase.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        readOnce();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btnSend)
    public void onBtnSendClicked() {
        String text = etMessage.getText().toString();
        if (TextUtils.isEmpty(text))return;

//        //reference to a table MyCoolChat
//        DatabaseReference chatTable = mDatabase.getReference("MyCoolChat");
//
//        //add a new Record: and get a reference to the new record
//        DatabaseReference currentRow = chatTable.push();
//
//        //get the value:
//        currentRow.setValue(text);

        mDatabase.getReference("chat").push().setValue(text);

        etMessage.setText(null);
    }

    private void setupRecycler (){
        ChatAdapter adapter = new ChatAdapter(mDatabase.getReference("chat"));
        rvChat.setAdapter(adapter);
        rvChat.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void readFromDb (){
        //get reference to table.
        DatabaseReference chatRef = mDatabase.getReference("chat");
        //final List<String> items = new ArrayList<>();
        //add a listener to the table.
        chatRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> rows = dataSnapshot.getChildren();

                for (DataSnapshot row : rows) {
                    String text = row.getValue(String.class);
                    //items.add(text);
                    Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void readOnce(){
        //get a reference to the table
        //add a listener

        //Get the data once from the server. Not updating unless we run the query again.
        mDatabase.getReference("chat").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> rows = dataSnapshot.getChildren();

                for (DataSnapshot row : rows) {
                    String text = row.getValue(String.class);
                    //items.add(text);
                    Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void readIncremental(){
        mDatabase.getReference("chat").addChildEventListener(new ChildEventListener() {

            //Once get aa the table:
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //Once get aa the table:
                String text = dataSnapshot.getValue(String.class);
                Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();


                //once a new item is added we will only get the new child
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    static class ChatAdapter extends FirebaseRecyclerAdapter<String, ChatAdapter.ChatViewHolder>{

        public ChatAdapter(Query ref) {
            super(String.class, R.layout.chat_item, ChatViewHolder.class, ref);
        }

        @Override
        protected void populateViewHolder(ChatViewHolder v, String text, int position) {
            v.tvchat.setText(text);
        }

        public static class ChatViewHolder extends RecyclerView.ViewHolder {
            TextView tvchat;

            public ChatViewHolder(View itemView) {
                super(itemView);
                tvchat = (TextView) itemView.findViewById(R.id.tvchat);
            }
        }
    }
}
