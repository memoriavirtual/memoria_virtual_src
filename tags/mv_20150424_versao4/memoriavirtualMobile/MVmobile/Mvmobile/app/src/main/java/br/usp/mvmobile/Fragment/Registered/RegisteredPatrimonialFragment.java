package br.usp.mvmobile.Fragment.Registered;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import br.usp.mvmobile.Model.Patrimonial;
import br.usp.mvmobile.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RegisteredPatrimonialFragment.OnRegistedInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RegisteredPatrimonialFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class RegisteredPatrimonialFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ArrayList<Patrimonial> patrimonialArray;
    private ListView mListView;

    private OnRegistedInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegisteredPatrimonialFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegisteredPatrimonialFragment newInstance(String param1, String param2) {
        RegisteredPatrimonialFragment fragment = new RegisteredPatrimonialFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public RegisteredPatrimonialFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        patrimonialArray = new ArrayList<Patrimonial>();
        patrimonialArray.add(new Patrimonial("Paineira","Daniele Boscolo"));
        patrimonialArray.add(new Patrimonial("Computador antigo","Airton Senna"));
        patrimonialArray.add(new Patrimonial("Monalisa","Leonardo da Vinci"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_registered_patrimonial, container, false);
        mListView = (ListView) view.findViewById(android.R.id.list);

        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onRegistedInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnRegistedInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //Carrega os dados do serviço
        loadDataListView();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnRegistedInteractionListener {
        // TODO: Update argument type and name
        public void onRegistedInteraction(Uri uri);
    }

    //Metodos
    public void loadDataListView() {

        if (patrimonialArray != null) {

            //canais na listView
            RegisteredPatrimonialAdapter adapter = new RegisteredPatrimonialAdapter(getActivity(), patrimonialArray);
            mListView.setAdapter(adapter);
        }
        else{
            //lista sem canais
            RegisteredPatrimonialAdapter adapter = new RegisteredPatrimonialAdapter(getActivity(), new ArrayList<Patrimonial>());
            mListView.setAdapter(adapter);
        }
    }

}
