using System;
using System.Collections.Generic;
using System.Linq;
using System.Linq.Expressions;
using System.Threading.Tasks;
using Catalog.API.Models;

namespace Catalog.API.DataAccess
{
    public interface ICatalogRepository<TEntity> where TEntity : class, ICatalogModel
    {
        Task<List<TEntity>> Get();
        Task<TEntity> Get(Guid id);
        Task<List<TEntity>> Get(Guid[] ids);
        Task<List<TEntity>> Get(Expression<Func<TEntity, bool>> filter, Func<IQueryable<TEntity>, IOrderedQueryable<TEntity>> orderBy = null);
        Task Insert(TEntity entity);
        Task Update(TEntity entity);
        Task Delete(Guid id);
        Task Delete(TEntity entity);
    }
}