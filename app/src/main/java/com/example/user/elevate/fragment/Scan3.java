package com.example.user.elevate.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.elevate.R;
import com.example.user.elevate.adapter.MyDataAdapter2;
import com.example.user.elevate.model.MyDataModel;
import com.example.user.elevate.parcer.JSONparser;
import com.example.user.elevate.util.InternetConnection;
import com.example.user.elevate.util.Keys;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

import me.anwarshahriar.calligrapher.Calligrapher;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Scan3.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Scan3#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Scan3 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Scan3() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Scan3.
     */
    // TODO: Rename and change types and number of parameters
    public static Scan3 newInstance(String param1, String param2) {
        Scan3 fragment = new Scan3();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private String scannedData;
    private ImageView scanBtn;
    private ListView listView;
    private ArrayList<MyDataModel> list;
    private MyDataAdapter2 adapter;
    private TextView text;
    private TextView text1,hadir,belum,scanr;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_scan3, container, false);
        Calligrapher calligrapher = new Calligrapher(this.getActivity());
        calligrapher.setFont(this.getActivity(),"American_Typewriter_Regular.ttf",true);
        scanBtn = (ImageView) v.findViewById(R.id.scan33);

        scanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentIntentIntegrator integrator = new FragmentIntentIntegrator(Scan3.this);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                integrator.setPrompt("Letakkan QR-Code secara Potrait");
                integrator.setBeepEnabled(false);
                integrator.setCameraId(0);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();
            }
        });
        list = new ArrayList<>();
        text= (TextView) v.findViewById(R.id.hadir3);
        text1= (TextView) v.findViewById(R.id.belumhadir3);
        hadir= (TextView) v.findViewById(R.id.hadiry);
        belum= (TextView) v.findViewById(R.id.belumy);
        scanr= (TextView) v.findViewById(R.id.scanR);
        text.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"American_Typewriter_Regular.ttf"));
        text1.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"American_Typewriter_Regular.ttf"));
        hadir.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"American_Typewriter_Regular.ttf"));
        belum.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"American_Typewriter_Regular.ttf"));
        scanr.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"American_Typewriter_Regular.ttf"));

        /**
         * Binding that List to Adapter
         */
        adapter = new MyDataAdapter2(getActivity(), list);

        /**
         * Getting List and Setting List Adapter
         */
        listView = (ListView) v.findViewById(R.id.listView3);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Snackbar.make(getView().findViewById(R.id.parentlayout3), list.get(position).getnama() + " sekarang " + list.get(position).getabsen1(), Snackbar.LENGTH_LONG).show();
            }
        });

        /**
         * Just to know onClick and Printing Hello Toast in Center.
         */
        if (InternetConnection.checkConnection(getActivity().getApplicationContext())) {
            new Scan3.GetDataTask().execute();
        } else {
            Snackbar.make(v, "Internet Connection Not Available", Snackbar.LENGTH_LONG).show();
        }

        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fab3);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(@NonNull View view) {

                /**
                 * Checking Internet Connection
                 */
                if (InternetConnection.checkConnection(getActivity().getApplicationContext())) {
                    new Scan3.GetDataTask().execute();



                } else {
                    Snackbar.make(view, "Internet Connection Not Available", Snackbar.LENGTH_LONG).show();
                }
            }
        });


        return v;

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    public final class FragmentIntentIntegrator extends IntentIntegrator {

        private final Fragment fragment;

        public FragmentIntentIntegrator(Fragment fragment) {
            super(fragment.getActivity());
            this.fragment = fragment;
        }
        @Override
        protected void startActivityForResult(Intent intent, int code) {
            fragment.startActivityForResult(intent, code);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(result!=null) {
            scannedData = result.getContents();
            if (scannedData != null) {
                // Here we need to handle scanned data...
                new SendRequest().execute();
                if (InternetConnection.checkConnection(getActivity().getApplicationContext())) {
                    new GetDataTask().execute();
                } else {
                    Snackbar.make(getView(), "Internet Connection Not Available", Snackbar.LENGTH_LONG).show();
                }

            }else {
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }



    public class SendRequest extends AsyncTask<String, Void, String> {


        protected void onPreExecute(){}

        protected String doInBackground(String... arg0) {

            try{

                //Enter script URL Here
                URL url = new URL("https://script.google.com/macros/s/AKfycbxfacYExpxpewn7PwdL26-tzUJSxjKvVb2YIw6-6Z4CNrPzZl0/exec");

                JSONObject postDataParams = new JSONObject();

                //int i;
                //for(i=1;i<=70;i++)


                //    String usn = Integer.toString(i);

                //Passing scanned code as parameter

                postDataParams.put("sdata",scannedData);


                Log.e("params",postDataParams.toString());

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(postDataParams));

                writer.flush();
                writer.close();
                os.close();

                int responseCode=conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader in=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuffer sb = new StringBuffer("");
                    String line="";

                    while((line = in.readLine()) != null) {

                        sb.append(line);
                        break;
                    }

                    in.close();
                    return sb.toString();

                }
                else {
                    return new String("false : "+responseCode);
                }
            }
            catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getActivity().getApplicationContext(), result,
                    Toast.LENGTH_LONG).show();

        }
    }

    public String getPostDataString(JSONObject params) throws Exception {

        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator<String> itr = params.keys();

        while(itr.hasNext()){

            String key= itr.next();
            Object value = params.get(key);

            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));

        }
        return result.toString();
    }
    class GetDataTask extends AsyncTask<Void, Void, Void> {

        ProgressDialog dialog;
        int jIndex;
        int x=0,y=0;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            /**
             * Progress Dialog for User Interaction
             */
            list = new ArrayList<>();
            /**
             * Binding that List to Adapter
             */
            adapter = new MyDataAdapter2(getActivity(), list);

            /**
             * Getting List and Setting List Adapter
             */
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override

                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Snackbar.make(getView().findViewById(R.id.parentlayout3), list.get(position).getnama() + " sekarang " + list.get(position).getabsen2(), Snackbar.LENGTH_LONG).show();
                }
            });
            jIndex=0;

            dialog = new ProgressDialog(getActivity());
            dialog.setTitle("Tunggu Sebentar...");
            dialog.setMessage("Update Data");
            dialog.show();
        }

        @Nullable
        @Override
        protected Void doInBackground(Void... params) {

            /**
             * Getting JSON Object from Web Using okHttp
             */
            JSONObject jsonObject = JSONparser.getDataFromWeb();

            try {
                /**
                 * Check Whether Its NULL???
                 */
                if (jsonObject != null) {
                    /**
                     * Check Length...
                     */
                    if(jsonObject.length() > 0) {
                        /**
                         * Getting Array named "contacts" From MAIN Json Object
                         */
                        JSONArray array = jsonObject.getJSONArray(Keys.KEY_CONTACTS);

                        /**
                         * Check Length of Array...
                         */
                        int lenArray = array.length(),belum,jml;
                        JSONObject innerObject ;
                        /*array = jsonObject.getJSONArray(Keys.KEY_CONTACTS);

                        x=innerObject.getString(Keys.KEY_totalabsen1);
                        Log.i("params",x);

                        Log.i("params",Integer.toString(lenArray));
                        jml=Integer.parseInt(x);

                        y=Integer.toString(belum);*/
                        if(lenArray > 0) {
                            for( ; jIndex < lenArray; jIndex++) {

                                /**
                                 * Creating Every time New Object
                                 * and
                                 * Adding into List
                                 */
                                MyDataModel model = new MyDataModel();

                                /**
                                 * Getting Inner Object from contacts array...
                                 * and
                                 * From that We will get Name of that Contact
                                 *
                                 */
                                innerObject = array.getJSONObject(jIndex);
                                String name = innerObject.getString(Keys.KEY_NAME);
                                String id = innerObject.getString(Keys.KEY_id);
                                String jam = innerObject.getString(Keys.KEY_jam3);
                                String country = innerObject.getString(Keys.KEY_absen3);
                                int bts=31-3-id.length();

                                name = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
                                if(jam.length()>0)
                                {
                                    jam=jam.substring(0,8).toString();
                                }
                                if(name.length()>bts)
                                {
                                    name=name.substring(0,bts-1)+"...";
                                }
                                if(!country.equals("Hadir"))
                                {
                                    country="Belum Hadir";
                                }
                                if(country.equals("Hadir"))
                                {
                                    x++;
                                }


                                /**
                                 * Getting Object from Object "phone"
                                 */
                                //JSONObject phoneObject = innerObject.getJSONObject(Keys.KEY_PHONE);
                                //String phone = phoneObject.getString(Keys.KEY_MOBILE);

                                model.setnama(name);
                                model.setId(id);
                                model.setabsen2(country);
                                model.settgl2(jam);
                                /**
                                 * Adding name and phone concatenation in List...
                                 */
                                list.add(model);
                            }
                            y=lenArray-x;
                        }
                    }
                } else {

                }
            } catch (JSONException je) {
                Log.i(JSONparser.TAG, "" + je.getLocalizedMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dialog.dismiss();
            /**
             * Checking if List size if more than zero then
             * Update ListView
             */
            if (list.size() > 0) {
                adapter.notifyDataSetChanged();
                text.setText(Integer.toString(x));
                text1.setText(Integer.toString(y));

            } else {
                Snackbar.make(getView().findViewById(R.id.parentlayout3), "No Data Found", Snackbar.LENGTH_LONG).show();
            }
        }
    }

}
