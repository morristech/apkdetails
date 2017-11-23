package uk.co.alt236.apkdetails.output;

import uk.co.alt236.apkdetails.decoder.ManifestParser;
import uk.co.alt236.apkdetails.model.AndroidManifest;
import uk.co.alt236.apkdetails.print.Coloriser;
import uk.co.alt236.apkdetails.print.section.SectionedKvPrinter;

import java.io.File;

public class ManifestInfoOutput implements Output {

    @Override
    public void output(SectionedKvPrinter printer, File file) {
        printer.add("AndroidManifest Info");
        printer.startKeyValueSection();
        try {
            final ManifestParser parser = new ManifestParser(file);
            final AndroidManifest manifest = parser.parse();
            printer.addKv("Application Id", manifest.getApplicationId());
            printer.addKv("Version Name", manifest.getVersionName());
            printer.addKv("Version Code", manifest.getVersionCode());
            printer.addKv("Minimum SDK", coloriseError(manifest.getMinSdkVersion()));
            printer.addKv("Compile SDK", coloriseError(manifest.getTargetSdkVersion()));
            printer.addKv("Build SDK", coloriseError(manifest.getPlatformBuildSdkVersion()));
            printer.addKv("Debuggable", manifest.isDebuggable());

            //System.out.println(manifest.getXml());
        } catch (Exception e) {
            printer.addKv("Parsing Error", Coloriser.error(e.toString()));
        }

        printer.endKeyValueSection();
    }

    private String coloriseError(final int input) {
        if (input < 0) {
            return Coloriser.error(input);
        } else {
            return String.valueOf(input);
        }
    }
}