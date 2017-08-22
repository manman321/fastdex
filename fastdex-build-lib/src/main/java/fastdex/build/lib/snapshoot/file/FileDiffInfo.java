package fastdex.build.lib.snapshoot.file;

import fastdex.build.lib.snapshoot.api.DiffInfo;
import fastdex.build.lib.snapshoot.api.Status;

/**
 * 目录对比，file.length或者file.lastModified不一样时判定文件发生变化
 * Created by tong on 17/3/29.
 */
public class FileDiffInfo extends DiffInfo<FileNode> {
    public FileDiffInfo() {
    }

    public FileDiffInfo(Status status, FileNode now, FileNode old) {
        super(status, (now != null ? now.getUniqueKey() : old.getUniqueKey()), now, old);
    }

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) return false;
        if (o == null || getClass() != o.getClass()) return false;

        FileDiffInfo that = (FileDiffInfo) o;

        if (status != that.status) return false;
        if (uniqueKey != null ? !uniqueKey.equals(that.uniqueKey) : that.uniqueKey != null) return false;

        if (now != null && !now.diffEquals(that.now)) {
            return false;
        }

        if (old != null && !old.diffEquals(that.old)) {
            return false;
        }
        return true;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        int nowLastModified = 0;

        if (now != null) {
            nowLastModified = (int) (((FileNode)now).lastModified ^ (((FileNode)now).lastModified >>> 32));
        }

        int oldLastModified = 0;
        if (old != null) {
            oldLastModified = (int) (((FileNode)old).lastModified ^ (((FileNode)old).lastModified >>> 32));
        }

        int nowFileLength = 0;
        int oldFileLength = 0;
        if (now != null) {
            nowFileLength = (int) (((FileNode)now).fileLength ^ (((FileNode)now).fileLength >>> 32));
        }
        if (old != null) {
            oldFileLength = (int) (((FileNode)old).fileLength ^ (((FileNode)old).fileLength >>> 32));
        }
        result = 31 * result + (now != null ? (now.hashCode() + nowLastModified + nowFileLength) : 0);
        result = 31 * result + (old != null ? (old.hashCode() + oldLastModified + oldFileLength) : 0);
        return result;
    }
}
