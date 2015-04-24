package mobile.memoriavirtual.usp.mvmobile.Fragment.ListaBemPatrimonial;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import mobile.memoriavirtual.usp.mvmobile.Activity.AddBemPatrimonialActivity;
import mobile.memoriavirtual.usp.mvmobile.Model.BemPatrimonial;
import mobile.memoriavirtual.usp.mvmobile.R;
import mobile.memoriavirtual.usp.mvmobile.Utils.Utils;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ListaBemPatrimonialFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ListaBemPatrimonialFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListaBemPatrimonialFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private ArrayList<BemPatrimonial> bemPatrimonialArray;
    private ListView mListView;
    private Button mAddButton;
    private OnFragmentInteractionListener mListener;

    public static ListaBemPatrimonialFragment newInstance(String param1, String param2) {
        ListaBemPatrimonialFragment fragment = new ListaBemPatrimonialFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public ListaBemPatrimonialFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        /*bemPatrimonialArray.add(new BemPatrimonial("Paineira","Daniele Boscolo"));
        bemPatrimonialArray.add(new BemPatrimonial("Computador antigo","Airton Senna"));
        bemPatrimonialArray.add(new BemPatrimonial("Monalisa","Leonardo da Vinci"));*/
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lista_bem_patrimonial, container, false);
        mListView = (ListView) view.findViewById(android.R.id.list);
        mAddButton = (Button) view.findViewById(R.id.lista_add_button);

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAddButtonClick(view);
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        bemPatrimonialArray = Utils.carregarBensPatrimoniaisSalvosCache();

        //Se nao tiver nenhum bem patrimonial cadastrado mostra mensagem
        if(bemPatrimonialArray == null || bemPatrimonialArray.size() == 0) {
            bemPatrimonialArray = new ArrayList<BemPatrimonial>();
            mListView.setVisibility(View.GONE);
        }
        else
        {
            //Carrega os dados na lista
            loadDataListView();
            mListView.setVisibility(View.VISIBLE);
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(Uri uri);
    }


    //Metodos
    public void loadDataListView() {
        ListaBemPatrimonialAdapter adapter = new ListaBemPatrimonialAdapter(getActivity(), bemPatrimonialArray);
        mListView.setAdapter(adapter);
    }

    //Click
    //Quando nao há bens cadastrados mostra botao para cadastrar
    public void onAddButtonClick(View v) {
        Intent intent = new Intent(getActivity(),AddBemPatrimonialActivity.class);
        startActivity(intent);
    }

}
