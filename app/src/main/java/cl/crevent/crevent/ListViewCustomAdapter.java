package cl.crevent.crevent;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ListViewCustomAdapter extends BaseAdapter
{
    public String title[];
    public String description[];
    public Activity context;
    public String urlimg[];
    public LayoutInflater inflater;

    public ListViewCustomAdapter(Activity context,String[] title, String[] description, String urlimg[]) {
        super();

        this.context = context;
        this.title = title;
        this.description = description;
        this.urlimg = urlimg;
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return title.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    public static class ViewHolder
    {
        ImageView imgViewLogo;
        TextView txtViewTitle;
        TextView txtViewDescription;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        ViewHolder holder;
        if(convertView==null)
        {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.list_item, null);

            holder.imgViewLogo = (ImageView) convertView.findViewById(R.id.img_empre);
            holder.txtViewTitle = (TextView) convertView.findViewById(R.id.tx_nom);
            holder.txtViewDescription = (TextView) convertView.findViewById(R.id.tx_dir);

            convertView.setTag(holder);
        }
        else
            holder=(ViewHolder)convertView.getTag();
        Picasso.with(context)
                .load(urlimg[position])
                .fit()
                .into(holder.imgViewLogo);

        holder.txtViewTitle.setText(title[position]);
        holder.txtViewDescription.setText(description[position]);
        return convertView;
    }

}