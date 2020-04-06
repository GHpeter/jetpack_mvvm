package  com.fuxing.plugin

import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin
import org.gradle.api.Project



public  class LifeCyclePlugin implements  Plugin<Project> {


    @Override
    void apply(Project project) {
        println ("add  private gradle plugin")
        def android=project.extensions.getByType(AppExtension)
        println '-------register autotracktransform-----'
        LifeCycleTransform transform=new LifeCycleTransform()
        android.registerTransform(transform)

    }
}