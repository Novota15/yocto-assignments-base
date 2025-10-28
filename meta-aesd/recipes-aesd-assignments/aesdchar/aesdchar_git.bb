# Recipe for aesdchar kernel module
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

inherit module

# Use the same repository as aesd-assignments
SRC_URI = "git://git@github.com/Novota15/aesd-assignments.git;protocol=ssh;branch=master \
           file://aesdchar-start-stop \
          "

PV = "1.0+git${SRCPV}"
# Set to reference the same commit hash as aesd-assignments
SRCREV = "4f4cda925e8636f68ed81d2743ec1d20c0ba7f7d"

# Build from the aesd-char-driver directory
S = "${WORKDIR}/git/aesd-char-driver"

# Files to install
FILES:${PN} += "${sysconfdir}/init.d/aesdchar-start-stop"
FILES:${PN} += "${base_libdir}/modules/${KERNEL_VERSION}/extra/aesdchar.ko"

# Inherit module class handles kernel module compilation
# We need to specify the kernel module name
KERNEL_MODULE_AUTOLOAD += "aesdchar"

do_install:append() {
    # Install init script
    install -d ${D}${sysconfdir}/init.d
    install -d ${D}${sysconfdir}/rcS.d
    install -d ${D}${sysconfdir}/rcK.d
    
    install -m 0755 ${WORKDIR}/aesdchar-start-stop ${D}${sysconfdir}/init.d/aesdchar-start-stop
    
    # Create symlinks for automatic startup
    ln -sf ../init.d/aesdchar-start-stop ${D}${sysconfdir}/rcS.d/S97aesdchar
    ln -sf ../init.d/aesdchar-start-stop ${D}${sysconfdir}/rcK.d/K97aesdchar
}
