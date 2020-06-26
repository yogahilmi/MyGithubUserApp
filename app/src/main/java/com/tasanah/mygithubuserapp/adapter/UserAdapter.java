package com.tasanah.mygithubuserapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tasanah.mygithubuserapp.R;
import com.tasanah.mygithubuserapp.model.User;

import java.util.ArrayList;
import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter extends BaseAdapter {

    private final Context context;
    private ArrayList<User> users = new ArrayList<>();

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public UserAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int i) {
        return users.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View itemView = view;
        if (itemView == null) {
            //Menghubungkan ViewHolder dengan View
            itemView = LayoutInflater.from(context).inflate(R.layout.listview_item, viewGroup, false);
        }

        ViewHolder viewHolder = new ViewHolder(itemView);

        //Mengubah nilai pahlawan sesuai dari posisinya
        User user = (User) getItem(i);
        viewHolder.bind(user);
        return itemView;
    }

    private class ViewHolder {
        private TextView txtName;
        private TextView txtRepo;
        private TextView txtPerusahaan;
        private CircleImageView imgPhoto;

        ViewHolder(View view) {
            txtName = view.findViewById(R.id.txt_nama);
            txtRepo = view.findViewById(R.id.txt_repo);
            txtPerusahaan = view.findViewById(R.id.txt_perusahaan);
            imgPhoto = view.findViewById(R.id.img_list);
        }

        void bind(final User user) {

            txtRepo.setText(context.getString(R.string.repository, user.getRepo()));
            txtName.setText(user.getName());
            txtPerusahaan.setText(user.getPerusahaan());
            imgPhoto.setImageResource(user.getPhoto());
        }
    }
}