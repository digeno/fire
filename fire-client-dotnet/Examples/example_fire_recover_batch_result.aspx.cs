﻿using FIRe;
using System;

public partial class example_fire_recover_batch_result : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {
        string dataB64 = Base64Encode("Hola Mundo!!");

        // Funcion del API de Clave Firma para cargar los datos a firmar
        FireBatchResult batchResult;
        string transactionId = "2c78060f-68fa-410c-9469-bdcc92bc4ff7";
        try
        {
            batchResult = new FireClient("A418C37E84BA").recoverBatchResult( // Identificador de la aplicacion (dada de alta previamente en el sistema)
                transactionId,   // Identificador de transaccion recuperado en la operacion createBatch()
                "00001"        // Identificador del usuario

            );
        }
        catch (Exception ex)
        {
            Result1.Text = ex.Message;
            return;
        }

        // Mostramos los datos obtenidos
        Result1.Text = batchResult.batch[0].id + " - " + batchResult.batch[0].ok + " - " + batchResult.batch[0].dt;
        Result2.Text = batchResult.batch[1].id + " - " + batchResult.batch[1].ok + " - " + batchResult.batch[1].dt;

    }

    /// <summary>Codifica en base64</summary>
    /// <param name="plainText">string a codificar.</param>
    /// <returns>string codificado en base 64 </returns>
    private static string Base64Encode(string plainText)
    {
        var plainTextBytes = System.Text.Encoding.UTF8.GetBytes(plainText);
        return System.Convert.ToBase64String(plainTextBytes);
    }
}