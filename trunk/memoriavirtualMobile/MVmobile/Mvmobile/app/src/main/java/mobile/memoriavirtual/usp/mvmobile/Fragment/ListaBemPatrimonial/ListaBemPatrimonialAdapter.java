package mobile.memoriavirtual.usp.mvmobile.Fragment.ListaBemPatrimonial;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.BemPatrimonial;
import mobile.memoriavirtual.usp.mvmobile.R;

public class ListaBemPatrimonialAdapter extends ArrayAdapter<BemPatrimonial> {

    private final Context context;
    private final List<BemPatrimonial> values;

    public ListaBemPatrimonialAdapter(Context context, List<BemPatrimonial> values) {
        super(context, R.layout.cell_lista_bem_patrimonial, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BemPatrimonial patrimonial = values.get(position);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.cell_lista_bem_patrimonial, parent, false);

        TextView name = (TextView) rowView.findViewById(R.id.registered_patrimonial_name);
        TextView author = (TextView) rowView.findViewById(R.id.registered_patrimonial_author);
        ImageView image = (ImageView) rowView.findViewById(R.id.registered_patrimonial_image);

        name.setText(patrimonial.getTituloPrincipal());
        author.setText("Tipo: " + patrimonial.getTipoBemPatrimonial());

        //Imagem
       /* if(patrimonial.getCadastro_image().size() > 0) {
            String image_data = patrimonial.getCadastro_image().get(0);
            image.setImageBitmap(Utils.stringToBitMap(image_data));
        }
*/
        return rowView;
    }

}
