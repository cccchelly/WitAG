package ezy.boost.update;

import android.content.Context;

import java.io.File;

/**
 * Created by Administrator on 2018-04-16.
 */

public interface IInstall {
    void install(Context context, File file, boolean force) throws Exception;
}
