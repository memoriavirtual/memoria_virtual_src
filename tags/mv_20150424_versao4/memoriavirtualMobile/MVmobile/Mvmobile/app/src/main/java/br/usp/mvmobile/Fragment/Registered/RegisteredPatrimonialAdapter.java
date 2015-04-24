package br.usp.mvmobile.Fragment.Registered;

import android.content.Context;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import br.usp.mvmobile.Model.Patrimonial;
import br.usp.mvmobile.R;

/**
 * Created by User on 01/10/2014.
 */
public class RegisteredPatrimonialAdapter extends ArrayAdapter<Patrimonial> {

    private final Context context;
    private final List<Patrimonial> values;

    public RegisteredPatrimonialAdapter(Context context, List<Patrimonial> values) {
        super(context, R.layout.registered_patrimonial_cell, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Patrimonial patrimonial = values.get(position);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.registered_patrimonial_cell, parent, false);

        TextView name = (TextView) rowView.findViewById(R.id.registered_patrimonial_name);
        TextView author = (TextView) rowView.findViewById(R.id.registered_patrimonial_author);

        name.setText(patrimonial.getName());
        author.setText("Autor: " + patrimonial.getAuthor());

        return rowView;
    }
}
