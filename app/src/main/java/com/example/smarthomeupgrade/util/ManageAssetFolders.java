package com.example.smarthomeupgrade.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;

import androidx.annotation.RequiresApi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public  class ManageAssetFolders extends AsyncTask<Void,Void,Void> {

    Context context;

    public ManageAssetFolders(Context context){
        super();
        this.context = context;
    }

    @Override
    protected Void doInBackground(Void... arg0)
    {
        manageAssetFolderToSDcard();
        return null;
    }

    private void manageAssetFolderToSDcard()
    {

        try
        {
            String arg_assetDir = context.getApplicationContext().getPackageName();
            String arg_destinationDir = context.getFilesDir().getAbsolutePath();
            File FolderInCache = new File(arg_destinationDir);
            if (!FolderInCache.exists())
            {
                copyDirorfileFromAssetManager(arg_assetDir, arg_destinationDir);
            }
        } catch (IOException e1)
        {

            e1.printStackTrace();
        }

    }


    public String copyDirorfileFromAssetManager(String arg_assetDir, String arg_destinationDir) throws IOException
    {
        File sd_path = Environment.getExternalStorageDirectory();
        String dest_dir_path = sd_path + addLeadingSlash(arg_destinationDir);
        File dest_dir = new File(dest_dir_path);

        createDir(dest_dir);

        AssetManager asset_manager = context.getApplicationContext().getAssets();
        String[] files = asset_manager.list(arg_assetDir);

        for (int i = 0; i < files.length; i++)
        {

            String abs_asset_file_path = addTrailingSlash(arg_assetDir) + files[i];
            String sub_files[] = asset_manager.list(abs_asset_file_path);

            if (sub_files.length == 0)
            {
                // It is a file
                String dest_file_path = addTrailingSlash(dest_dir_path) + files[i];
                copyAssetFile(abs_asset_file_path, dest_file_path);
            } else
            {
                // It is a sub directory
                copyDirorfileFromAssetManager(abs_asset_file_path, addTrailingSlash(arg_destinationDir) + files[i]);
            }
        }

        return dest_dir_path;
    }


    public void copyAssetFile(String assetFilePath, String destinationFilePath) throws IOException
    {
        InputStream in = context.getApplicationContext().getAssets().open(assetFilePath);
        OutputStream out = new FileOutputStream(destinationFilePath);

        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0)
            out.write(buf, 0, len);
        in.close();
        out.close();
    }

    public String addTrailingSlash(String path)
    {
        if (path.charAt(path.length() - 1) != '/')
        {
            path += "/";
        }
        return path;
    }

    public String addLeadingSlash(String path)
    {
        if (path.charAt(0) != '/')
        {
            path = "/" + path;
        }
        return path;
    }

    public void createDir(File dir) throws IOException
    {
        if (dir.exists())
        {
            if (!dir.isDirectory())
            {
                throw new IOException("Can't create directory, a file is in the way");
            }
        } else
        {
            dir.mkdirs();
            if (!dir.isDirectory())
            {
                throw new IOException("Unable to create directory");
            }
        }
    }
}