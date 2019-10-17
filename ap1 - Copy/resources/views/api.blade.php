<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
    <title>Document</title>
</head>
<?php
$client = new GuzzleHttp\Client();
$res = $client->request('GET', 'https://jsonplaceholder.typicode.com/posts');
$isi = $res->getBody();
$isidadi = json_decode($isi);
$banyak = count($isidadi);
$userId=" ";
$bookId=" ";
$judulBuku = "";
if (isset($_GET['lihat'])) {
    $idBuku = $_GET['idku'];
    $client1 = new GuzzleHttp\Client();
    $res1 = $client1->request('GET', 'https://jsonplaceholder.typicode.com/posts/'.$idBuku);
    $isi1 = $res1->getBody();
    $isidadi1 = json_decode($isi);
    $judulBuku = $isidadi1[$idBuku-1]->title;
    $userId = $isidadi1[$idBuku-1]->userId;
    $bookId = $isidadi1[$idBuku-1]->id;
}
?>
<body>

<div class="jumbotron text-center">
    <h1>Informasi Upload Buku</h1>
    <p>Berisi informasi mengenai buku yang telah diupload</p>
</div>

<div class="container" style="width: 40%">
    <form action="" method="get">
    <div class="dropdown">
        <label for="idku">Pilih Id Buku :</label>
        <select name="idku" class="form-control">
            <?php
            for ($x = 0; $x<$banyak;$x++){
                echo '<option value="'.$isidadi[$x]->id.'">'.$isidadi[$x]->id.'</option>';
            }
            ?>
        </select>
    </div>
        <br>
        <center><input type="submit" name="lihat" value="Submit"  class="btn btn-primary"></center>
    </form>
</div>
<br>
<div class="container" style="width: 40%">
    <div class="row">
        <div class="col" style="background-color: lavender">
            <br>
            <h6>User Id :</h6>
            <p><?php echo $userId ?></p>
            <h6>Id Buku :</h6>
            <p><?php echo $bookId ?></p>
            <h6>Judul Buku :</h6>
            <p><?php echo $judulBuku ?></p>
        </div>
    </div>
</div>
<br>
</body>
</html>

