package com.galleryExplorerMobile.core.ui

import android.view.View

class SnackBarModel {

    var mensaje: String? = null
    var icono: Int = -1
    var prioridad: SNACKBAR_PRIORITY = SNACKBAR_PRIORITY.NORMAL
    var tipo: SNACKBAR_TIPO? = null
    var actionText: String? = null
    var actionListener: View.OnClickListener? = null

    constructor(mensaje: String?, tipo: SNACKBAR_TIPO?, prioridad: SNACKBAR_PRIORITY, icono: Int) {
        this.mensaje = mensaje
        this.icono = icono
        this.prioridad = prioridad
        this.tipo = tipo
    }

    constructor(mensaje: String?, tipo: SNACKBAR_TIPO?, prioridad: SNACKBAR_PRIORITY) :
            this(mensaje, tipo, prioridad, -1)

    constructor(mensaje: String?, tipo: SNACKBAR_TIPO?) :
            this(mensaje, tipo, SNACKBAR_PRIORITY.NORMAL)


    enum class SNACKBAR_TIPO {
        ERROR
    }

    enum class SNACKBAR_PRIORITY(val value: Int) {
        NORMAL(1),
    }
}