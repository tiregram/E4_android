package com.tiregram.glove.bluetoothglove;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;

import org.rajawali3d.Object3D;
import org.rajawali3d.lights.DirectionalLight;
import org.rajawali3d.loader.ParsingException;
import org.rajawali3d.materials.Material;
import org.rajawali3d.materials.methods.DiffuseMethod;
import org.rajawali3d.materials.textures.ATexture;
import org.rajawali3d.materials.textures.Texture;
import org.rajawali3d.primitives.Sphere;
import org.rajawali3d.renderer.RajawaliRenderer;


import org.rajawali3d.loader.LoaderOBJ;

/**
 * Created by Stephen on 23/02/2017.
 */

public class Renderer extends RajawaliRenderer {

    public Context context;


    private DirectionalLight directionalLight;
    private Sphere earthSphere;
    private Object3D mObjectGroup;

    private float inputX = 0;
    private float inputY = 0;
    private float inputZ = 0;

    private float inputRotX = 0;
    private float inputRotY = 0;
    private float inputRotZ = 0;

    public Renderer(Context context)
    {
        super(context);
        this.context = context;
        setFrameRate(60);

    }






    @Override
    protected void initScene() {


        directionalLight = new DirectionalLight(1f, .2f, -1.0f);
        directionalLight.setColor(1.0f, 1.0f, 1.0f);
        directionalLight.setPower(2);
        getCurrentScene().addLight(directionalLight);

        Material material = new Material();
        material.enableLighting(true);
        material.setDiffuseMethod(new DiffuseMethod.Lambert());
        material.setColor(0);

        Texture earthTexture = new Texture("Earth", R.mipmap.earthtruecolo_nasa_big);
        try{
            material.addTexture(earthTexture);

        } catch (ATexture.TextureException error){
            Log.d("DEBUG", "TEXTURE ERROR");
        }

        //Debug SPhere with Texture
        //earthSphere = new Sphere(1, 24, 24);
        //earthSphere.setMaterial(material);
        //getCurrentScene().addChild(earthSphere);
        getCurrentCamera().setZ(10.2f);


        LoaderOBJ objParser = new LoaderOBJ(mContext.getResources(),mTextureManager, R.raw.handfree);
        try {
            objParser.parse();
            mObjectGroup = objParser.getParsedObject();
            getCurrentScene().addChild(mObjectGroup);
            mObjectGroup.setPosition(0.0,-1.0,0.0);
            mObjectGroup.setRotation(90.0,180.0,50.0);
            mObjectGroup.setScale(0.05);

        } catch (ParsingException e) {
            // e.printStackTrace();
        }


    }


    //Here we are going to update the values
    @Override
    public void onRender(final long elapsedTime, final double deltaTime) {

        super.onRender(elapsedTime, deltaTime);

        updateGyroInput();
        updatePositionInput();



        //earthSphere.rotate(Vector3.Axis.Y, 1.0);
        mObjectGroup.setPosition(inputX,inputY,inputZ);
        mObjectGroup.setRotation(inputRotX,inputRotY,inputRotZ);



    }



    void updatePositionInput()
    {
        //To Fill
        inputX=0;
        inputY=0;
        inputZ=0;
    }


    void updateGyroInput()
    {
        //To Fill
        inputRotX = 0;
        inputRotY = 180;
        inputRotZ = 0;
    }
    @Override
    public void onOffsetsChanged(float xOffset, float yOffset, float xOffsetStep, float yOffsetStep, int xPixelOffset, int yPixelOffset) {

    }

    @Override
    public void onTouchEvent(MotionEvent event) {

    }
}
