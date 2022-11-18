#region Help:  Introduction to the Script Component
/* The Script Component allows you to perform virtually any operation that can be accomplished in
 * a .Net application within the context of an Integration Services data flow.
 *
 * Expand the other regions which have "Help" prefixes for examples of specific ways to use
 * Integration Services features within this script component. */
#endregion

#region Namespaces
using System;
using System.Collections.Generic;
using System.Data;
using System.IO;
using System.Reflection;
using System.Text;
using System.Web.Script.Serialization;
using Microsoft.SqlServer.Dts.Pipeline.Wrapper;
using Microsoft.SqlServer.Dts.Runtime.Wrapper;
using System.Linq;
using System.Globalization;
using Microsoft.SqlServer.Dts.Pipeline;

#endregion

/// <summary>
/// This is the class to which to add your code.  Do not change the name, attributes, or parent
/// of this class.
/// </summary>
[Microsoft.SqlServer.Dts.Pipeline.SSISScriptComponentEntryPointAttribute]
public class ScriptMain : UserComponent
{
    #region Help:  Using Integration Services variables and parameters
    /* To use a variable in this script, first ensure that the variable has been added to
     * either the list contained in the ReadOnlyVariables property or the list contained in
     * the ReadWriteVariables property of this script component, according to whether or not your
     * code needs to write into the variable.  To do so, save this script, close this instance of
     * Visual Studio, and update the ReadOnlyVariables and ReadWriteVariables properties in the
     * Script Transformation Editor window.
     * To use a parameter in this script, follow the same steps. Parameters are always read-only.
     *
     * Example of reading from a variable or parameter:
     *  DateTime startTime = Variables.MyStartTime;
     *
     * Example of writing to a variable:
     *  Variables.myStringVariable = "new value";
     */
    #endregion

    #region Help:  Using Integration Services Connnection Managers
    /* Some types of connection managers can be used in this script component.  See the help topic
     * "Working with Connection Managers Programatically" for details.
     *
     * To use a connection manager in this script, first ensure that the connection manager has
     * been added to either the list of connection managers on the Connection Managers page of the
     * script component editor.  To add the connection manager, save this script, close this instance of
     * Visual Studio, and add the Connection Manager to the list.
     *
     * If the component needs to hold a connection open while processing rows, override the
     * AcquireConnections and ReleaseConnections methods.
     * 
     * Example of using an ADO.Net connection manager to acquire a SqlConnection:
     *  object rawConnection = Connections.SalesDB.AcquireConnection(transaction);
     *  SqlConnection salesDBConn = (SqlConnection)rawConnection;
     *
     * Example of using a File connection manager to acquire a file path:
     *  object rawConnection = Connections.Prices_zip.AcquireConnection(transaction);
     *  string filePath = (string)rawConnection;
     *
     * Example of releasing a connection manager:
     *  Connections.SalesDB.ReleaseConnection(rawConnection);
     */
    #endregion

    #region Help:  Firing Integration Services Events
    /* This script component can fire events.
     *
     * Example of firing an error event:
     *  ComponentMetaData.FireError(10, "Process Values", "Bad value", "", 0, out cancel);
     *
     * Example of firing an information event:
     *  ComponentMetaData.FireInformation(10, "Process Values", "Processing has started", "", 0, fireAgain);
     *
     * Example of firing a warning event:
     *  ComponentMetaData.FireWarning(10, "Process Values", "No rows were received", "", 0);
     */
    #endregion

    /// <summary>
    /// This method is called once, before rows begin to be processed in the data flow.
    ///
    /// You can remove this method if you don't need to do anything here.
    /// </summary>
    public override void PreExecute()
    {
        base.PreExecute();
        /*
         * Add your code here
         */
    }

    /// <summary>
    /// This method is called after all the rows have passed through this component.
    ///
    /// You can delete this method if you don't need to do anything here.
    /// </summary>
    public override void PostExecute()
    {
        base.PostExecute();
        /*
         * Add your code here
         */
    }

    public override void CreateNewOutputRows()
    {
        /*
          Add rows by calling the AddRow method on the member variable named "<Output Name>Buffer".
          For example, call MyOutputBuffer.AddRow() if your output was named "MyOutput".
        */
        FileStream fileStream = File.OpenRead("C:\\Users\\anczo\\Documents\\SQL Server Management Studio\\Hurtownia\\Movies_all.json");

        using (StreamReader sr = new StreamReader(fileStream))
        {
            string line;
            var serializer = new JavaScriptSerializer();
            serializer.MaxJsonLength = Int32.MaxValue;
            int id = 1;
            sr.ReadLine();

            try
            {
                while ((line = sr.ReadLine()) != null)
                {

                    if (line[0] == '{' || line[0] == '}' || line.Length <= 4)
                        continue;


                    if (line[line.Length - 2] == '}' && line[line.Length - 3] == '}' && line[line.Length - 4] == '}' && line[line.Length - 5] == '}')
                        continue;

                    if (line[line.Length - 1] == ',')
                        line = '{' + line.Remove(line.Length - 1) + '}';
                    else
                        line = '{' + line + '}';

                    Dictionary<string, object> result = (Dictionary<string, object>)serializer.DeserializeObject(line);

                    KeyValuePair<string, object> tmp = result.ElementAt(0);
                    Dictionary<string, object> tmp1 = (Dictionary<string, object>)tmp.Value;

                    Dictionary<string, object> info = (Dictionary<string, object>)tmp1.ElementAt(0).Value;
                    Dictionary<string, object> crew = (Dictionary<string, object>)tmp1.ElementAt(2).Value;
                    Dictionary<string, object> cast = (Dictionary<string, object>)tmp1.ElementAt(1).Value;
                    Dictionary<string, object> ratings = (Dictionary<string, object>)tmp1.ElementAt(3).Value;


                    if (info.Count < 3 || ratings.Count < 1)
                    {
                        continue;
                    }

                    MoviesBuffer.AddRow();

                    MoviesBuffer.title = info.ElementAt(0).Value.ToString();
                    MoviesBuffer.year = info.ElementAt(1).Value.ToString();
                    MoviesBuffer.isAdult = int.Parse(info.ElementAt(2).Value.ToString());
                    int a = 0;
                    string myint = (int.TryParse(info.ElementAt(3).Value.ToString(), out a) == true) ? info.ElementAt(3).Value.ToString() : null;
                    MoviesBuffer.runtime = myint;
                    MoviesBuffer.genre1 = info.ElementAt(4).Value.ToString();
                    MoviesBuffer.genre2 = info.ElementAt(5).Value.ToString();
                    MoviesBuffer.genre3 = info.ElementAt(6).Value.ToString();


                    //MovieFactBuffer.infoFK = id;

                    /* Crew */

                    MoviesBuffer.crewnumber = crew.Count;

                    for (int i = 0; i < crew.Count; i++)
                    {
                        Dictionary<string, object> tmpCrew = (Dictionary<string, object>)crew.ElementAt(i).Value;

                        CrewListBuffer.AddRow();
                        CrewListBuffer.CrewDimFK = id;
                        CrewListBuffer.type = tmpCrew.ElementAt(0).Value.ToString();

                        if (tmpCrew.ElementAt(1).Value == null)
                        {
                            CrewListBuffer.name = "";
                        }
                        else
                        {
                            CrewListBuffer.name = tmpCrew.ElementAt(1).Value.ToString();
                        }

                    }

                    MoviesBuffer.crewFK = id;



                    /* cast */



                    MoviesBuffer.castnumber = cast.Count;

                    for (int i = 0; i < cast.Count; i++)
                    {
                        Dictionary<string, object> tmpCast = (Dictionary<string, object>)cast.ElementAt(i).Value;

                        CastListBuffer.AddRow();
                        CastListBuffer.CastDimFK = id + 1;

                        var test1 = tmpCast.ElementAt(0).Value;
                        if (test1 == null)
                        {
                            CastListBuffer.name = null;
                        }
                        else
                        {
                            CastListBuffer.name = test1.ToString();
                        }

                        var test = tmpCast.ElementAt(3).Value;
                        if (test == null)
                        {
                            CastListBuffer.character = null;
                        }
                        else
                        {
                            CastListBuffer.character = test.ToString();
                        }


                        string myint1 = (int.TryParse(info.ElementAt(1).Value.ToString(), out a) == true) ? info.ElementAt(1).Value.ToString() : null;
                        CastListBuffer.birth = myint1;

                        string myint2 = (int.TryParse(info.ElementAt(2).Value.ToString(), out a) == true) ? info.ElementAt(2).Value.ToString() : null;
                        CastListBuffer.death = myint2;
                    }



                    MoviesBuffer.castFK = id;


                    /* ratings */
                    double sum = 0.0;

                    MoviesBuffer.numberOfVotes = ratings.Count;

                    for (int i = 0; i < ratings.Count; i++)
                    {
                        Dictionary<string, object> tmpRatings = (Dictionary<string, object>)ratings.ElementAt(i).Value;

                        RatingListBuffer.AddRow();
                        RatingListBuffer.RatingDimFK = id;

                        RatingListBuffer.userID = int.Parse(tmpRatings.ElementAt(0).Value.ToString());
                        RatingListBuffer.rating = (float)double.Parse(tmpRatings.ElementAt(1).Value.ToString(), CultureInfo.InvariantCulture);

                        sum += double.Parse(tmpRatings.ElementAt(1).Value.ToString(), CultureInfo.InvariantCulture);
                    }
                    MoviesBuffer.avgRating = (decimal)Math.Round((sum / (float)ratings.Count), 3);

                    MoviesBuffer.ratingsFK = id;

                    id++;
                }
            }
            catch (Exception ex)
            {
                Console.Write("jhhhh");
            }
        }



    }

}
