package com.upds.farmalook

import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*

import android.widget.EditText
import android.widget.Button.*
import android.view.View
import android.widget.Toast
import androidx.annotation.NonNull
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_maps.*
import java.lang.StringBuilder
import kotlin.collections.ArrayList

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private lateinit var lastLocation: Location

    private lateinit var mDatabase: DatabaseReference

    private val tmpRealTimeMarkers = ArrayList<Marker>()
    private val realTimeMarkers = ArrayList<Marker>()

    //private android.widget.Button.Button boton;
    companion object{
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

    override fun onMarkerClick(p0: Marker?) = false

        private lateinit var mMap: GoogleMap
       // private lateinit var ArrayList<Marker> RealTIme = new ArrayList<>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        //Base de datos de marcadores Farmacias
        mDatabase = FirebaseDatabase.getInstance().getReference()
        /*val myRef = mDatabase.setValue("Hola Mundo")*/
    }

    //Busqueda de farmacias o medicamentos




    fun onClick(view:View){
        Toast.makeText(this,"hoooooola",Toast.LENGTH_LONG).show()
    }

    /**
    * Manipula el mapa una vez disponible.
    * Esta devolución de llamada se activa cuando el mapa está listo para ser utilizado.
    * Aquí es donde podemos agregar marcadores o líneas, agregar oyentes o mover la cámara. En este caso,
    * solo agregamos un marcador cerca de Sydney, Australia.
    * Si los servicios de Google Play no están instalados en el dispositivo, se le solicitará al usuario que lo instale
    * dentro del SupportMapFragment. Este método solo se activará una vez que el usuario tenga
    * instaló los servicios de Google Play y regresó a la aplicación.
     */

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

       /* // Añadir marcador para capturar con la cámara
        val potosi = LatLng(-19.5580403, -65.7540632)
        mMap.addMarker(MarkerOptions().position(potosi).draggable(true).title("Ciudad en Potosí").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)))
                //.icon(BitmapDescriptorFactory.fromResource(R.drawable.potosi)))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(potosi, 10F))
        @Nonnull*/
        //Log.d("Prueba","***********************************************")
      mDatabase.child("farmalook-41cee.Farmacias").addValueEventListener(object: ValueEventListener {
          //var getdata = object : ValueEventListener {
              override fun onCancelled(error: DatabaseError) {
              }

              override fun onDataChange(dataSnapshot: DataSnapshot) {
                  /*for (marker in realTimeMarkers){
                    marker.remove()
                }*/
                  /*for(postSnapshot in dataSnapshot.children){
                    val mf: MapsFarm = postSnapshot.getValue(MapsFarm::class.java)!!
                    val nombre: String = mf.getNombre()
                    val direccion: String = mf.getDireccion()
                    val telefono: String = mf.getTelefono()
                    val latitud: Double = mf.getLatitud()
                    val longitud: Double = mf.getLongitud()

                    val farm = "Nombre: $nombre"
                    val dir= "Dir: $direccion"
                    val telf = "Tel: $telefono"

                    val markerOptions = MarkerOptions()
                    markerOptions.position(LatLng(latitud, longitud))
                    .title("farmacia").snippet(dir).snippet(telf)
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_farmacias))

                    //Para que no se vayan juntando los marcadores y se borren
                    tmpRealTimeMarkers.add(mMap.addMarker(markerOptions))
                }
                realTimeMarkers.clear()
                realTimeMarkers.addAll(tmpRealTimeMarkers)
            }*/

                  var sb = StringBuilder()
                  for (i in dataSnapshot.children) {
                      var nombre = i.child("nombre")
                      var direccion = i.child("direccion")
                      var telefono = i.child("telefono")
                      var latitud = i.child("latitud")
                      var longitud = i.child("longitud")

                      val markerOptions = MarkerOptions()
                          //markerOptions.position(LatLng(latidud, longitud))
                          //   .title("farmacias").snippet(direccion)
                          .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_farmacias))

                      sb.append("${i.key} $nombre $direccion $telefono $latitud $longitud")

                  }
              }


      })
          /* mDatabase.addValueEventListener(getdata)
        mDatabase.addListenerForSingleValueEvent(getdata)*/


          /* val circleOptions = CircleOptions()
            .center(LatLng(37.4, -122.1))
            .radius(4000.0)
            .strokeWidth(10f)
            .strokeColor(Color.GREEN)
            .fillColor(Color.argb(128, 255, 0, 0))
            .clickable(true)
        val circle = mMap.addCircle(circleOptions)*/

        //Controles UI settings
        mMap.setOnMapClickListener{this}
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isCompassEnabled = true

        //Permisos de ejecucion
        // Permisos normales y peligrosos   https://www.youtube.com/redirect?v=5d7HWpcQMTE&event=video_description&redir_token=8AhWphUMn_TM9e81ly_Kg4WRpgB8MTU4NzA2OTY3NUAxNTg2OTgzMjc1&q=https%3A%2F%2Fdeveloper.android.com%2Fguide%2Ftopics%2Fsecurity%2Fpermissions.html%23normal-dangerous
        // Permisos en tiempo de ejecucion  https://www.youtube.com/redirect?v=5d7HWpcQMTE&event=video_description&redir_token=8AhWphUMn_TM9e81ly_Kg4WRpgB8MTU4NzA2OTY3NUAxNTg2OTgzMjc1&q=https%3A%2F%2Fdeveloper.android.com%2Ftraining%2Fpermissions%2Frequesting.html
        setUpMap()
    }

    //Agregar el marcador de la ubicacion
    private fun placeMarker(location: LatLng){
        val markerOptions = MarkerOptions().position(location).title("Yo")
            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
        //markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))

        mMap.addMarker(markerOptions)
    }

    //Chequea si tiene el permiso especifico ACCESS_FINE_LOCATION
    private fun setUpMap(){
        if (ActivityCompat.checkSelfPermission(this,
           android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
          ActivityCompat.requestPermissions(this,
              arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
          return
        }

        mMap.isMyLocationEnabled = true

        fusedLocationClient.lastLocation.addOnSuccessListener{location ->
            if(location != null){
                lastLocation = location
                val currentLatLong= LatLng(location.latitude, location.longitude)
                placeMarker((currentLatLong))
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLong, 15f))
            }
        }

    }
}
