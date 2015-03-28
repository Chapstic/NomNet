package app.nomnet;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

/**
 * Created by Izzy on 3/28/2015.
 * NomPopulator is a RecyclerView Adapter that will fill the noms with their data
 */
public class NomPopulator extends RecyclerView.Adapter<NomPopulator.NomViewHolder> {

    private LayoutInflater inflater;
    List<Nom> noms = Collections.emptyList();

    public NomPopulator(Context context, List<Nom> data){
        inflater = LayoutInflater.from(context);
        this.noms = data;
    }

    @Override
    public NomPopulator.NomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.nom_row, parent, false);
        NomViewHolder nomViewHolder = new NomViewHolder(view);
        return nomViewHolder;
    }

    @Override
    public void onBindViewHolder(NomPopulator.NomViewHolder holder, int position) {
        Nom cur = noms.get(position);
        holder.title.setText(cur.title);
        holder.image.setImageResource(cur.image);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class NomViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView title;
        public NomViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.feed_nom_text);
            image = (ImageView) itemView.findViewById(R.id.feed_nom_image);
        }
    }
}
