package info.vourja.airline.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.vourja.airline.Model.AirLineActivity;
import info.vourja.airline.R;


public class ActivityGridAdapter extends ArrayAdapter<AirLineActivity> {

    private ActivityGridAdapterListener listener;

    public ActivityGridAdapter(Context context) {
        super(context, R.layout.fragment_home_grid);
    }

    public void bindListener(ActivityGridAdapterListener listener) {
        this.listener = listener;
    }

    public void unBindListener() {
        this.listener = null;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        final AirLineActivity airLineActivity = getItem(position);

        if(convertView == null || convertView.getTag() == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_home_grid, parent, false);
            holder = new ViewHolder(convertView);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }
        convertView.setTag(holder);

        holder.title.setText(airLineActivity.getName());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null) {
                    listener.onClickItem(v, airLineActivity);
                }
            }
        });
        return convertView;
    }

    class ViewHolder {
        @BindView(R.id.title)
        TextView title;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public interface ActivityGridAdapterListener {
        public void onClickItem(View view, AirLineActivity airlinActivity);
    }

}
