/* UTILITIES RELATED TO OBJECT/FILE STORAGE */

const aws = require('aws-sdk');
const multerS3 = require('multer-s3');
const multer = require('multer');
const uuidv1 = require('uuid/v1');

// set s3 endpoint to endpoint in env var
const spacesEndpoint = new aws.Endpoint(`${process.env.AWS_S3_ENDPOINT}`);

// init and configure s3 to prepare it for multer
const s3 = new aws.S3({
    endpoint: spacesEndpoint,
    accessKeyId: `${process.env.AWS_S3_KEY}`,
    secretAccessKey: `${process.env.AWS_S3_SECRET}`
});

// setup multer storage object using multerS3
const storage = multerS3({
    s3: s3,
    bucket: 'flerbo',
    acl: 'public-read',
    key: function (request, file, cb) {
        const obsfucatedFilename = uuidv1();
        cb(null, obsfucatedFilename);
    }
});

// create image fulter object and fn to error out files with non-image extensions
const imageFilter = function (req, file, callback) {
    if (file.originalname.match(/\.(jpg|jpeg|png|gif)$/)) {
        callback(null, true);
    } else {
        callback(new Error('OnlyImageFilesAllowed'), false);
    }
};

// export multer as uploadImage and add file count and size limits
exports.uploadImage = multer({
    storage: storage,
    fileFilter: imageFilter,
    limits: {fileSize: 2000000, files: 1}
});
