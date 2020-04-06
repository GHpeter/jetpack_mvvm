import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import com.android.utils.FileUtils
import com.fuxing.asm.LifeCycleClassVisitor
import groovy.io.FileType
import groovyjarjarasm.asm.ClassWriter
import jdk.internal.org.objectweb.asm.ClassReader
import jdk.internal.org.objectweb.asm.ClassVisitor

public class LifeCycleTransform extends Transform {

    @Override
    String getName() {
        return "LifeCycleTransform"
    }

    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.PROJECT_ONLY
    }

    @Override
    boolean isIncremental() {
        return false
    }

    @Override
    void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        Collection<TransformInput> tfinput = transformInvocation.inputs
        TransformOutputProvider provider = transformInvocation.outputProvider

        if (provider != null) {
            provider.deleteAll()
        }
        tfinput.each {
            TransformInput transinput ->
                transinput.directoryInputs.each {
                    DirectoryInput input ->
                        File dir = input.file
                        if (dir) {
                            dir.traverse(type: FileType.FILES, nameFilter: ~/.*\.class/) {
                                File file ->
                                    println("find class:" + file.name)
                                    ClassReader classReader = new ClassReader(file.bytes)
                                    ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS)
                                    ClassVisitor visitor = new LifeCycleClassVisitor(classWriter)
                                    classReader.accept(visitor, groovyjarjarasm.asm.ClassReader.EXPAND_FRAMES)
                                    byte[] bytes = classWriter.toByteArray()


                                    FileOutputStream outputStream = new FileOutputStream(file.path)
                                    outputStream.write(bytes)
                                    outputStream.close()
                            }
                        }
                        def dest = provider.getContentLocation(input.name, input.contentTypes, input.scopes, Format.DIRECTORY)
                        FileUtils.copyDirectory(input.file, dest)

                }
        }


    }
}