
<?php
$mdp = "";
$url="";
$user="";
$dataName="";
$db = mysqli_connect($url,$user,$mdp,$user);
if($_SERVER['REQUEST_METHOD']== 'GET') {
    if (!$db) {
        echo "ERROR";
        die('Connexion au serveur impossible : ' . mysqli_connect_errno() . ' ; ' . mysqli_connect_error());

    }

    $request = "SELECT * FROM $dataName";
    $result = mysqli_query($db, $request);
    $rows = array();
    while ($r = mysqli_fetch_assoc($result)) {
        $r["answerStr"] = json_decode($r["answerStr"]);
        $rows[] = $r;

    }
    echo json_encode($rows);
    mysqli_close($db);
}
elseif ($_SERVER['REQUEST_METHOD'] == 'POST')
{
    $content = trim(file_get_contents("php://input"));
     $infoRequest = substr($content,0,6);
    if ($_SERVER["CONTENT_TYPE"] ==="application/json; charset=utf8") {

        if ($infoRequest === 'Delete') {
            $object = json_decode(substr($content, 6));
            $question = $object->question;
            $answerStr = json_encode(json_encode($object->answerStr));
            $numberAnswer = $object->numberAnswer;

            $request = "Delete FROM $dataName WHERE question =\"" . $question . "\" AND numberAnswer =\"" . $numberAnswer . "\" AND answerStr= $answerStr";
            $result = mysqli_query($db, $request);
            $rows = array();
            while ($r = mysqli_fetch_assoc($result)) {
                $rows[] = $r;
            }
            echo "OK";
        } elseif ($infoRequest === 'Modify') {
            $object = json_decode(substr($content, 6));
            $questionOld = $object[0];
            $questionNew = $object[1];

            $oldQuestion = $questionOld->question;
            $oldAnswerStr = json_encode(json_encode($questionOld->answerStr));
            $oldNumberAnswer = $questionOld->numberAnswer;

            $newQuestion = $questionNew->question;
            $newAnswerStr =json_encode(json_encode($questionNew->answerStr));
            $newNumberAnswer = $questionNew->numberAnswer;


            $request = "UPDATE $dataName Set question =\"$newQuestion\",numberAnswer=\"$newNumberAnswer\",answerStr=$newAnswerStr  WHERE question =\"". $oldQuestion . "\" AND numberAnswer =\"" . $oldNumberAnswer . "\" AND answerStr= $oldAnswerStr";
            mysqli_query($db,$request);
            echo "OK";
        }
        elseif($infoRequest ==='add   ')
        {
            $object = json_decode(substr($content,6));
            $question = $object->question;
            $answerStr = json_encode(json_encode($object->answerStr));
            $numberAnswer = $object->numberAnswer;
            $request = "INSERT INTO $dataName(question,numberAnswer,answerStr)VALUE (\"$question\",$numberAnswer,$answerStr)";

            mysqli_query($db,$request);
            echo "OK";
        }
        elseif($infoRequest ==='DelALL') {
            $request = "DELETE FROM $dataName";
            mysqli_query($db,$request);
            echo"OK";

        }
    }
    else{
        $question = "Question 3";
        $test = array("1", "2", "3");
        $answerStr = json_encode(json_encode($test));
        $numberAnswer = 2;
        $request = "INSERT INTO $dataName(question,numberAnswer,answerStr)VALUE (\"$question\",$numberAnswer,$answerStr)";
        mysqli_query($db, $request);
    }
}
mysqli_close($db);
?>
